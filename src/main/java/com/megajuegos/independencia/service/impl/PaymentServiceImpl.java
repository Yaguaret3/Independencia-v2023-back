package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.ResourceCard;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import com.megajuegos.independencia.enums.ResourceTypeEnum;
import com.megajuegos.independencia.exceptions.PriceNotFoundException;
import com.megajuegos.independencia.repository.CardRepository;
import com.megajuegos.independencia.service.PaymentService;
import com.megajuegos.independencia.service.util.PaymentRequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final CardRepository cardRepository;

    @Override
    public Boolean succesfulPay(PlayerData playerData, PaymentRequestUtil request, PersonalPricesEnum toBuy) {

        List<PersonalPrice> currentPrices = playerData.getPrices();

        PersonalPrice priceToPay = currentPrices.stream().filter(p -> p.getName() == toBuy).findFirst()
                .orElseThrow(() -> new PriceNotFoundException());

        return paymentIsValid(playerData, request, priceToPay);
    }

    private Boolean paymentIsValid(PlayerData playerData,
                                   PaymentRequestUtil request,
                                   PersonalPrice priceToPay) {

        if(playerData instanceof GobernadorData
                && !gobernadorPaymentIsValid((GobernadorData) playerData, request, priceToPay)){
            return false;
        }
        if(playerData instanceof MercaderData
                && !mercaderPaymentIsValid((MercaderData) playerData, request, priceToPay)){
            return false;
        }
        if(playerData instanceof RevolucionarioData
                && !revolucionarioPaymentIsValid((RevolucionarioData) playerData, request, priceToPay)){
            return false;
        }
        return generalPaymentIsValid(playerData, request, priceToPay);
    }

    private Boolean gobernadorPaymentIsValid(GobernadorData gobernadorData,
                                             PaymentRequestUtil request,
                                             PersonalPrice priceToPay){

        return request.getPlata() <= gobernadorData.getPlata()
                && request.getPlata() >= priceToPay.getPlata();
    }

    private Boolean mercaderPaymentIsValid(MercaderData mercaderData,
                                           PaymentRequestUtil request,
                                           PersonalPrice priceToPay){
        return request.getPuntajeComercial() <= mercaderData.getPuntajeComercial()
                && request.getPuntajeComercial() >= priceToPay.getPuntajeComercial();
    }

    private Boolean revolucionarioPaymentIsValid(RevolucionarioData revolucionarioData,
                                                 PaymentRequestUtil request,
                                                 PersonalPrice priceToPay){
        return true;
    }

    private Boolean generalPaymentIsValid(PlayerData playerData,
                                          PaymentRequestUtil request,
                                          PersonalPrice priceToPay){

        List<ResourceCard> resources = cardRepository.findResourceCardByIdIn(request.getResourcesIds());

        long textilEnviado = resources.stream().filter(r -> r.getResourceTypeEnum()==ResourceTypeEnum.TEXTIL).count();
        long textilAPagar = priceToPay.getTextil();
        long textilActual = 0L;
        long agropecuariaEnviada = resources.stream().filter(r -> r.getResourceTypeEnum()==ResourceTypeEnum.AGROPECUARIA).count();
        long agropecuariaAPagar = priceToPay.getAgropecuaria();
        long agropecuariaActual = 0L;
        long metalmecanicaEnviada = resources.stream().filter(r -> r.getResourceTypeEnum()==ResourceTypeEnum.METALMECANICA).count();
        long metalmecanicaAPagar = priceToPay.getMetalmecanica();
        long metalmecanicaActual = 0L;
        long construccionEnviada = resources.stream().filter(r -> r.getResourceTypeEnum()==ResourceTypeEnum.CONSTRUCCION).count();
        long construccionAPagar = priceToPay.getConstruccion();
        long construccionActual = 0L;
        long comercialEnviada = resources.stream().filter(r -> r.getResourceTypeEnum()==ResourceTypeEnum.COMERCIAL).count();
        long comercialAPagar = priceToPay.getComercial();
        long comercialActual = 0L;

        for(Card card : playerData.getCards()){
            if(card instanceof ResourceCard
                    && ((ResourceCard) card).getResourceTypeEnum()==ResourceTypeEnum.TEXTIL
                    && !card.isAlreadyPlayed()){
                textilActual ++;
            }
            if(card instanceof ResourceCard
                    && ((ResourceCard) card).getResourceTypeEnum()==ResourceTypeEnum.AGROPECUARIA
                    && !card.isAlreadyPlayed()){
                agropecuariaActual ++;
            }
            if(card instanceof ResourceCard
                    && ((ResourceCard) card).getResourceTypeEnum()==ResourceTypeEnum.METALMECANICA
                    && !card.isAlreadyPlayed()){
                metalmecanicaActual ++;
            }
            if(card instanceof ResourceCard
                    && ((ResourceCard) card).getResourceTypeEnum()==ResourceTypeEnum.CONSTRUCCION
                    && !card.isAlreadyPlayed()){
                construccionActual ++;
            }
            if(card instanceof ResourceCard
                    && ((ResourceCard) card).getResourceTypeEnum()==ResourceTypeEnum.COMERCIAL
                    && !card.isAlreadyPlayed()){
                comercialActual ++;
            }
        }

        return textilEnviado <= textilActual
                && textilEnviado >= textilAPagar

                && agropecuariaEnviada <= agropecuariaActual
                && agropecuariaEnviada >= agropecuariaAPagar

                && metalmecanicaEnviada <= metalmecanicaActual
                && metalmecanicaEnviada >= metalmecanicaAPagar

                && construccionEnviada <= construccionActual
                && construccionEnviada >= construccionAPagar

                && comercialEnviada <= comercialActual
                && comercialEnviada >= comercialAPagar;
    }
}
