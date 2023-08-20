package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.capitan.*;
import com.megajuegos.independencia.dto.response.CapitanResponse;
import com.megajuegos.independencia.entities.Battle;
import com.megajuegos.independencia.entities.DisciplineSpent;
import com.megajuegos.independencia.entities.GameSubRegion;
import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.entities.card.ActionCard;
import com.megajuegos.independencia.entities.card.BattleCard;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.entities.data.MercaderData;
import com.megajuegos.independencia.enums.ActionTypeEnum;
import com.megajuegos.independencia.enums.BattleTypeEnum;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import com.megajuegos.independencia.exceptions.BattleNotFoundException;
import com.megajuegos.independencia.exceptions.GameAreaNotFoundException;
import com.megajuegos.independencia.exceptions.PaymentNotPossibleException;
import com.megajuegos.independencia.exceptions.PlayerNotFoundException;
import com.megajuegos.independencia.repository.BattleRepository;
import com.megajuegos.independencia.repository.GameSubRegionRepository;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import com.megajuegos.independencia.repository.data.CapitanDataRepository;
import com.megajuegos.independencia.service.CapitanService;
import com.megajuegos.independencia.service.PaymentService;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CapitanServiceImpl implements CapitanService {

    private final UserIndependenciaRepository userRepository;
    private final GameSubRegionRepository gameSubRegionRepository;
    private final BattleRepository battleRepository;
    private final PaymentService paymentService;
    private final UserUtil userUtil;
    private final CapitanDataRepository capitanDataRepository;

    @Override
    public CapitanResponse getData() {
        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        return CapitanResponse.toDtoResponse(capitanData);
    }

    @Override
    public void buyActionCards(BuyRequest request) throws InstanceNotFoundException {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(capitanData, request.getPayment(), PersonalPricesEnum.ACCION_CARD)) throw new PaymentNotPossibleException();

        ActionTypeEnum actionType = ActionTypeEnum.fromId(request.getCardTypeId());
        capitanData.getCards().add(ActionCard.builder()
                        .tipoAccion(actionType)
                        .build());
        capitanDataRepository.save(capitanData);
    }

    @Override
    public void buyBattleCards(BuyRequest request) throws InstanceNotFoundException {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
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

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        Integer turno = capitanData.getGameData().getTurno();

        for(Card card : capitanData.getCards()){

            if(Objects.equals(card.getId(), request.getCardId())){
                ActionCard actionCard = (ActionCard) card;
                actionCard.setTurnWhenPlayed(turno);
                actionCard.setAlreadyPlayed(true);
            }
        }
        capitanDataRepository.save(capitanData);
    }

    @Override
    public void playBattleCards(BattleRequest request) {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        Battle battle = battleRepository.findById(request.getBattleId())
                .orElseThrow(BattleNotFoundException::new);

        for(Card card : capitanData.getCards()){

            if(Objects.equals(card.getId(), request.getCardId())){
                BattleCard battleCard = (BattleCard) card;
                battleCard.setBatalla(battle);
                battleCard.setAlreadyPlayed(true);
            }
        }
        capitanDataRepository.save(capitanData);
    }

    @Override
    public void makeCamp(CampRequest request) {
        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if (Boolean.FALSE.equals(paymentService.succesfulPay(capitanData, request.getPayment(), PersonalPricesEnum.CAMP))) throw new PaymentNotPossibleException();

        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getNewAreaId())
                .orElseThrow(() -> new GameAreaNotFoundException());

        capitanData.getCamp().setGameSubRegion(gameSubRegion);

        capitanDataRepository.save(capitanData);
    }

    @Override
    public void upgradeCamp(BuyRequest request) {

        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(capitanData, request.getPayment(), PersonalPricesEnum.CAMP)) throw new PaymentNotPossibleException();

        capitanData.getCamp().setNivel(capitanData.getCamp().getNivel()+1);
        capitanDataRepository.save(capitanData);
    }

    @Override
    public void spendDiscipline(DisciplineRequest request) {
        CapitanData capitanData = capitanDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(capitanData, request.getPayment(), PersonalPricesEnum.DISCIPLINE_SPENT)) throw new PaymentNotPossibleException();

        Battle battle = battleRepository.findById(request.getBattle())
                .orElseThrow(() -> new BattleNotFoundException());

        battle.getDisciplinaUsada().add(DisciplineSpent.builder()
                        .capitanId(capitanData.getId())
                        .disciplineSpent(request.getPayment().getDisciplina())
                        .build());
        capitanDataRepository.save(capitanData);
        battleRepository.save(battle);
    }
}
