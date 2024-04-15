package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.capitan.*;
import com.megajuegos.independencia.dto.response.CapitanResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyCapitanResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.entities.*;
import com.megajuegos.independencia.entities.card.ActionCard;
import com.megajuegos.independencia.entities.card.BattleCard;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.entities.data.MercaderData;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.ActionTypeEnum;
import com.megajuegos.independencia.enums.BattleTypeEnum;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import com.megajuegos.independencia.enums.PhaseEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.CapitanDataRepository;
import com.megajuegos.independencia.service.CapitanService;
import com.megajuegos.independencia.service.PaymentService;
import com.megajuegos.independencia.service.util.GameIdUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
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

    @Override
    public CapitanResponse getData() {
        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        return CapitanResponse.toDtoResponse(capitanData);
    }

    @Override
    public GameDataTinyCapitanResponse getGameData() {
        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        return GameDataTinyCapitanResponse.toDtoResponse(capitanData);
    }

    @Override
    public void buyActionCards(BuyRequest request) throws InstanceNotFoundException {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(capitanData, request.getPayment(), PersonalPricesEnum.fromId(request.getCardTypeId()))) throw new PaymentNotPossibleException();

        ActionTypeEnum actionType = ActionTypeEnum.fromId(request.getCardTypeId());
        capitanData.getCards().add(ActionCard.builder()
                        .tipoAccion(actionType)
                        .build());
        capitanDataRepository.save(capitanData);
    }

    @Override
    public void buyBattleCards(BuyRequest request) throws InstanceNotFoundException {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(capitanData, request.getPayment(), PersonalPricesEnum.BATTLE_CARD)) throw new PaymentNotPossibleException();

        BattleTypeEnum battleType = BattleTypeEnum.fromId(request.getCardTypeId());
        capitanData.getCards().add(BattleCard.builder()
                        .tipoOrdenDeBatalla(battleType)
                        .build());
        capitanDataRepository.save(capitanData);
    }

    @Override
    public void playActionRequest(ActionRequest request) {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        Integer turno = capitanData.getGameData().getTurno();

        Card card = capitanData.getCards().stream()
                .filter(c -> c.getId()
                        .equals(request.getCardId()))
                .findFirst().orElseThrow(() -> new CardNotFoundException());

        if(!(card instanceof ActionCard)){
            throw new IncorrectCardTypeException();
        }

        ActionCard actionCard = (ActionCard) card;

        if(Arrays.asList(ActionTypeEnum.ACAMPE, ActionTypeEnum.MOVIMIENTO, ActionTypeEnum.REACCION).contains(actionCard.getTipoAccion())){
            throw new IncorrectPhaseException();
        }

        switch(actionCard.getTipoAccion()){
            case DESPLIEGUE:
                deploy(request.getSubregionId(), capitanData);
                break;
            case ATAQUE:
                attack(request.getSubregionId(), capitanData);
                break;
            case DEFENSA:
                defend(capitanData);
                break;
            default:
                throw new IncorrectActionTypeException();
        }

        actionCard.setTurnWhenPlayed(turno);
        actionCard.setAlreadyPlayed(true);
        cardRepository.save(actionCard);
    }

    @Override
    public void rush(ActionRequest request) {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        Integer turno = capitanData.getGameData().getTurno();

        Card card = capitanData.getCards().stream().filter(c -> c.getId().equals(request.getCardId())).findFirst().orElseThrow(() -> new CardNotFoundException());

        if(!(card instanceof ActionCard)){
            throw new IncorrectCardTypeException();
        }
        card.setTurnWhenPlayed(turno);
        card.setAlreadyPlayed(true);
        cardRepository.save(card);
    }

    @Override
    public void assignMilitiaToBattle(Long battleId, Integer milicia) {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

        Battle battle = battleRepository.findById(battleId).orElseThrow(() -> new BattleNotFoundException());

        Army army = battle.getCombatientes().stream()
                .filter(a -> a.getCapitanData().equals(capitanData) && a.getMilicias().equals(0))
                .findFirst()
                .orElseThrow(() -> new IncorrectBattleException());

        army.setMilicias(milicia);
        armyRepository.save(army);

    }

    @Override
    public void playBattleCards(BattleRequest request) {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        Battle battle = battleRepository.findById(request.getBattleId())
                .orElseThrow(BattleNotFoundException::new);
        Integer turno = capitanData.getGameData().getTurno();

        Army army = battle.getCombatientes().stream().filter(b -> b.getCapitanData().equals(capitanData)).findFirst().orElseThrow(IncorrectBattleException::new);

        Card card = capitanData.getCards().stream().filter(c -> c.getId().equals(request.getCardId())).findFirst()
                .orElseThrow(() -> new CardNotFoundException());

        if(!(card instanceof BattleCard)){
            throw new IncorrectCardTypeException();
        }

        army.getCartasJugadas().add((BattleCard) card);

        battleRepository.save(battle);
        card.setTurnWhenPlayed(turno);
        card.setAlreadyPlayed(true);
        cardRepository.save(card);
    }

    @Override
    public void move(MovementRequest request) {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        Integer turno = capitanData.getGameData().getTurno();

        if(!PhaseEnum.MOVING.equals(capitanData.getGameData().getFase())){
            throw new IncorrectPhaseException();
        }

        Card card = capitanData.getCards().stream()
                .filter(c -> c.getId().equals(request.getCardId()))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException());
        if(!(card instanceof ActionCard)){
            throw new IncorrectCardTypeException();
        }
        ActionCard actionCard = (ActionCard) card;
        if(!ActionTypeEnum.MOVIMIENTO.equals(actionCard.getTipoAccion())){
            throw new IncorrectActionTypeException();
        }

        GameRegion regionTo =  gameRegionRepository.findById(request.getRegionToId())
                .orElseThrow(() -> new RegionNotFoundException());

        if(!capitanData.getCamp().getGameRegion().getRegionEnum().getAdyacentes().contains(regionTo.getRegionEnum())){
            throw new RegionNotAdjacentException();
        }

        actionCard.setTurnWhenPlayed(turno);
        actionCard.setAlreadyPlayed(true);
        cardRepository.save(actionCard);
    }

    @Override
    public void makeCamp(CampRequest request) {
        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());
        Integer turno = capitanData.getGameData().getTurno();

        if(!PhaseEnum.MOVING.equals(capitanData.getGameData().getFase())){
            throw new IncorrectPhaseException();
        }

        Card card = capitanData.getCards().stream()
                .filter(c -> c.getId().equals(request.getCampCardId()))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException());

        if(!(card instanceof ActionCard)){
            throw new IncorrectCardTypeException();
        }

        ActionCard actionCard = (ActionCard) card;
        if(!ActionTypeEnum.ACAMPE.equals(actionCard.getTipoAccion())){
            throw new IncorrectActionTypeException();
        }

        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getNewAreaId())
                .orElseThrow(() -> new GameAreaNotFoundException());

        capitanData.getCamp().setGameSubRegion(gameSubRegion);

        actionCard.setTurnWhenPlayed(turno);
        actionCard.setAlreadyPlayed(true);
        cardRepository.save(actionCard);
        capitanDataRepository.save(capitanData);
    }

    private void deploy(Long subregionId, CapitanData capitanData){
        GameSubRegion gameSubRegionDeploy = gameSubRegionRepository.findById(subregionId)
                .orElseThrow(() -> new SubRegionNotFoundException());
        gameSubRegionDeploy.getEjercitos().add(Army.builder()
                .capitanData(capitanData)
                .gameSubRegion(gameSubRegionDeploy)
                .build());
        gameSubRegionRepository.save(gameSubRegionDeploy);
    }
    private void attack(Long subregionId, CapitanData capitanData){
        GameSubRegion gameSubRegionAttack = gameSubRegionRepository.findById(subregionId)
                .orElseThrow(() -> new SubRegionNotFoundException());
        gameSubRegionAttack.getAttackActions().add(Action.builder()
                .capitanId(capitanData)
                .actionType(ActionTypeEnum.ATAQUE)
                .solved(false)
                .build());
        gameSubRegionRepository.save(gameSubRegionAttack);
    }
    private void defend(CapitanData capitanData){
        GameRegion gameRegion = capitanData.getCamp().getGameRegion();
        gameRegion.getDefenseActions().add(Action.builder()
                .capitanId(capitanData)
                .actionType(ActionTypeEnum.DEFENSA)
                .solved(false)
                .build());
        gameRegionRepository.save(gameRegion);
    }
}
