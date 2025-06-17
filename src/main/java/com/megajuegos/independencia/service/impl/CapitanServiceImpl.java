package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.capitan.*;
import com.megajuegos.independencia.dto.response.CapitanResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyCapitanResponse;
import com.megajuegos.independencia.entities.*;
import com.megajuegos.independencia.entities.card.ActionCard;
import com.megajuegos.independencia.entities.card.BattleCard;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.*;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.CapitanDataRepository;
import com.megajuegos.independencia.service.CapitanService;
import com.megajuegos.independencia.service.PaymentService;
import com.megajuegos.independencia.service.util.GameIdUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceNotFoundException;
import java.util.Arrays;
import java.util.Objects;

import static com.megajuegos.independencia.enums.ActionTypeEnum.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CapitanServiceImpl implements CapitanService {

    private final GameSubRegionRepository gameSubRegionRepository;
    private final BattleRepository battleRepository;
    private final PaymentService paymentService;
    private final UserUtil userUtil;
    private final CapitanDataRepository capitanDataRepository;
    private final CardRepository cardRepository;
    private final GameRegionRepository gameRegionRepository;
    private final ArmyRepository armyRepository;
    private final GameIdUtil gameIdUtil;
    private final LogRepository logRepository;
    private final ActionRepository actionRepository;

    @Override
    public CapitanResponse getData() {
        CapitanData capitanData = getPlayerData();
        return CapitanResponse.toDtoResponse(capitanData);
    }

    @Override
    public GameDataTinyCapitanResponse getGameData() {
        CapitanData capitanData = getPlayerData();
        return GameDataTinyCapitanResponse.toDtoResponse(capitanData);
    }

    @Override
    public void buyActionCards(BuyRequest request) throws InstanceNotFoundException {

        CapitanData capitanData = getPlayerData();
        PersonalPricesEnum priceEnum = capitanData.getPrices().stream()
                .filter(p -> request.getCardTypeId().equals(p.getId()))
                .map(PersonalPrice::getName)
                .findFirst()
                .orElseThrow(PriceNotFoundException::new);

        if (Boolean.FALSE.equals(paymentService.isPaymentSuccessful(capitanData, request.getPayment(), priceEnum)))
            throw new PaymentNotPossibleException();

        ActionTypeEnum actionType = ActionTypeEnum.fromName(priceEnum.name());
        Card card = ActionCard.builder()
                .tipoAccion(actionType)
                .playerData(capitanData)
                .build();
        capitanData.getCards().add(card);

        cardRepository.save(card);
        capitanDataRepository.save(capitanData);

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.RECIBIDO)
                .nota(String.format("Compraste una carta de acción: %s", actionType.getNombre()))
                .player(capitanData)
                .build();

        logRepository.save(log);
    }

    @Override
    public void buyBattleCards(BuyRequest request) throws InstanceNotFoundException {

        CapitanData capitanData = getPlayerData();
        PersonalPricesEnum priceEnum = capitanData.getPrices().stream()
                .filter(p -> request.getCardTypeId().equals(p.getId()))
                .map(PersonalPrice::getName)
                .findFirst()
                .orElseThrow(PriceNotFoundException::new);

        if (Boolean.FALSE.equals(paymentService.isPaymentSuccessful(capitanData, request.getPayment(), priceEnum)))
            throw new PaymentNotPossibleException();

        BattleTypeEnum battleType = BattleTypeEnum.fromName(priceEnum.name());

        Card card = BattleCard.builder()
                .tipoOrdenDeBatalla(battleType)
                .playerData(capitanData)
                .build();
        capitanData.getCards().add(card);

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.RECIBIDO)
                .nota(String.format("Compraste una orden de batalla: %s", battleType.getNombre()))
                .player(capitanData)
                .build();

        logRepository.save(log);
        capitanData.getLogs().add(log);

        cardRepository.save(card);
        capitanDataRepository.save(capitanData);
    }

    @Override
    public void playActionRequest(ActionRequest request) {

        CapitanData capitanData = getPlayerData();
        Integer turno = capitanData.getGameData().getTurno();

        Card card = capitanData.getCards().stream()
                .filter(c -> c.getId()
                        .equals(request.getCardId()))
                .findFirst().orElseThrow(() -> new CardNotFoundException(request.getCardId()));

        if (!(card instanceof ActionCard)) {
            throw new IncorrectCardTypeException();
        }

        ActionCard actionCard = (ActionCard) card;

        if (Arrays.asList(ActionTypeEnum.ACAMPE, ActionTypeEnum.MOVIMIENTO, ActionTypeEnum.REACCION).contains(actionCard.getTipoAccion())) {
            throw new IncorrectPhaseException();
        }

        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getSubregionId())
                .orElseThrow(() -> new SubRegionNotFoundException(request.getSubregionId()));

        switch (actionCard.getTipoAccion()) {
            case DESPLIEGUE:
                deploy(gameSubRegion, capitanData);
                break;
            case ATAQUE:
                attack(gameSubRegion, capitanData);
                break;
            case DEFENSA:
                defend(capitanData);
                break;
            default:
                throw new IncorrectActionTypeException(actionCard.getTipoAccion());
        }

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Jugaste una carta de acción: %s en %s",
                        actionCard.getTipoAccion().getNombre(),
                        gameSubRegion.getSubRegionEnum().getNombre()))
                .player(capitanData)
                .build();

        logRepository.save(log);
        capitanData.getLogs().add(log);

        actionCard.setTurnWhenPlayed(turno);
        actionCard.setAlreadyPlayed(true);
        cardRepository.save(actionCard);
    }

    @Override
    public void rush(ActionRequest request) {

        CapitanData capitanData = getPlayerData();
        Integer turno = capitanData.getGameData().getTurno();

        Card card = capitanData.getCards().stream().filter(c -> c.getId().equals(request.getCardId())).findFirst().orElseThrow(() -> new CardNotFoundException(request.getCardId()));

        if (!(card instanceof ActionCard && ((ActionCard) card).getTipoAccion().equals(REACCION))) {
            throw new IncorrectCardTypeException();
        }

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Jugaste una carta de acción: %s", REACCION))
                .player(capitanData)
                .build();

        logRepository.save(log);

        card.setTurnWhenPlayed(turno);
        card.setAlreadyPlayed(true);
        cardRepository.save(card);
    }

    @Override
    public void assignMilitiaToBattle(Long battleId, Integer milicia) {

        CapitanData capitanData = getPlayerData();

        Battle battle = battleRepository.findById(battleId).orElseThrow(() -> new BattleNotFoundException(battleId));

        Army army = battle.getCombatientes().stream()
                .filter(a -> a.getCapitanData().equals(capitanData) && a.getMilicias().equals(0))
                .findFirst()
                .orElseThrow(IncorrectBattleException::new);

        army.setMilicias(milicia);
        armyRepository.save(army);

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Asignaste %s milicias a la batalla en ", milicia, battle.getSubregion().getNombre()))
                .player(capitanData)
                .build();

        logRepository.save(log);
    }

    @Override
    public void playBattleCards(BattleRequest request) {

        CapitanData capitanData = getPlayerData();
        Battle battle = battleRepository.findById(request.getBattleId())
                .orElseThrow(() -> new BattleNotFoundException(request.getBattleId()));
        Integer turno = capitanData.getGameData().getTurno();

        Army army = battle.getCombatientes().stream().filter(b -> b.getCapitanData().equals(capitanData)).findFirst().orElseThrow(IncorrectBattleException::new);

        Card card = capitanData.getCards().stream().filter(c -> c.getId().equals(request.getCardId())).findFirst()
                .orElseThrow(() -> new CardNotFoundException(request.getCardId()));

        if (!(card instanceof BattleCard)) {
            throw new IncorrectCardTypeException();
        }

        BattleCard battleCard = (BattleCard) card;

        army.getCartasJugadas().add(battleCard);

        battleCard.setArmy(army);

        armyRepository.save(army);
        battleRepository.save(battle);

        card.setTurnWhenPlayed(turno);
        card.setAlreadyPlayed(true);
        cardRepository.save(card);

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Jugaste la orden de batalla %s durante el combate en %s",
                        battleCard.getTipoOrdenDeBatalla().getNombre(),
                        battle.getSubregion().getNombre()))
                .player(capitanData)
                .build();

        logRepository.save(log);
    }

    @Override
    public void move(MovementRequest request) {

        CapitanData capitanData = getPlayerData();
        Integer turno = capitanData.getGameData().getTurno();

        if (!PhaseEnum.MOVING.equals(capitanData.getGameData().getFase())) {
            throw new IncorrectPhaseException();
        }

        Card card = capitanData.getCards().stream()
                .filter(c -> c.getId().equals(request.getCardId()))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(request.getCardId()));
        if (!(card instanceof ActionCard)) {
            throw new IncorrectCardTypeException();
        }
        ActionCard actionCard = (ActionCard) card;
        if (!ActionTypeEnum.MOVIMIENTO.equals(actionCard.getTipoAccion())) {
            throw new IncorrectActionTypeException(actionCard.getTipoAccion());
        }

        GameRegion regionTo = gameRegionRepository.findById(request.getRegionToId())
                .orElseThrow(() -> new RegionNotFoundException(request.getRegionToId()));

        if (!capitanData.getCamp().getGameRegion().getRegionEnum().getAdyacentes().contains(regionTo.getRegionEnum())) {
            throw new RegionNotAdjacentException(capitanData.getCamp().getGameRegion().getRegionEnum(), regionTo.getRegionEnum());
        }

        actionCard.setTurnWhenPlayed(turno);
        actionCard.setAlreadyPlayed(true);
        cardRepository.save(actionCard);

        actionRepository.save(Action.builder()
                .capitanId(capitanData)
                .actionType(ActionTypeEnum.DESPLIEGUE)
                .solved(false)
                .gameRegion(regionTo)
                .build());

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Jugaste la carta de acción de %s ", MOVIMIENTO))
                .player(capitanData)
                .build();

        logRepository.save(log);
    }

    @Override
    public void makeCamp(CampRequest request) {
        CapitanData capitanData = getPlayerData();
        Integer turno = capitanData.getGameData().getTurno();

        if (!PhaseEnum.MOVING.equals(capitanData.getGameData().getFase())) {
            throw new IncorrectPhaseException();
        }

        Card card = capitanData.getCards().stream()
                .filter(c -> c.getId().equals(request.getCampCardId()))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(request.getCampCardId()));

        if (!(card instanceof ActionCard)) {
            throw new IncorrectCardTypeException();
        }

        ActionCard actionCard = (ActionCard) card;
        if (!ActionTypeEnum.ACAMPE.equals(actionCard.getTipoAccion())) {
            throw new IncorrectActionTypeException(actionCard.getTipoAccion());
        }

        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getNewAreaId())
                .orElseThrow(() -> new SubRegionNotFoundException(request.getNewAreaId()));

        capitanData.getCamp().setSubregion(gameSubRegion);

        actionCard.setTurnWhenPlayed(turno);
        actionCard.setAlreadyPlayed(true);
        cardRepository.save(actionCard);
        capitanDataRepository.save(capitanData);

        Log log = Log.builder()
                .turno(capitanData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Jugaste la carta de acción de %s", ACAMPE))
                .player(capitanData)
                .build();

        logRepository.save(log);
    }

    private void deploy(GameSubRegion gameSubRegionDeploy, CapitanData capitanData) {

        actionRepository.save(Action.builder()
                .capitanId(capitanData)
                .actionType(ActionTypeEnum.DESPLIEGUE)
                .solved(false)
                .subregion(gameSubRegionDeploy)
                .build());
    }

    private void attack(GameSubRegion gameSubRegionAttack, CapitanData capitanData) {

        actionRepository.save(Action.builder()
                .capitanId(capitanData)
                .actionType(ActionTypeEnum.ATAQUE)
                .solved(false)
                .subregion(gameSubRegionAttack)
                .build());
    }

    private void defend(CapitanData capitanData) {
        GameRegion gameRegion = capitanData.getCamp().getGameRegion();
        actionRepository.save(Action.builder()
                .capitanId(capitanData)
                .actionType(DEFENSA)
                .solved(false)
                .gameRegion(gameRegion)
                .build());
    }

    private CapitanData getPlayerData() {
        Long playerId = userUtil.getCurrentUser().getPlayerDataList().stream()
                .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                .findFirst()
                .map(PlayerData::getId)
                .orElseThrow(PlayerNotFoundException::new);
        return capitanDataRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
}
