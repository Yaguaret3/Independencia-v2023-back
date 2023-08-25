package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.mercader.*;
import com.megajuegos.independencia.dto.response.MercaderResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.entities.GameSubRegion;
import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.Route;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.MarketCard;
import com.megajuegos.independencia.entities.card.ResourceCard;
import com.megajuegos.independencia.entities.data.MercaderData;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import com.megajuegos.independencia.enums.ResourceTypeEnum;
import com.megajuegos.independencia.enums.SubRegionEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.CardRepository;
import com.megajuegos.independencia.repository.GameSubRegionRepository;
import com.megajuegos.independencia.repository.RouteRepository;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
import com.megajuegos.independencia.repository.data.MercaderDataRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.MercaderService;
import com.megajuegos.independencia.service.PaymentService;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MercaderServiceImpl implements MercaderService {

    private final UserIndependenciaRepository userRepository;
    private final CardRepository cardRepository;
    private final PaymentService paymentService;
    private final UserUtil userUtil;
    private final MercaderDataRepository mercaderDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final RouteRepository routeRepository;
    private final GameSubRegionRepository subregionRepository;

    @Override
    public MercaderResponse getData() {
        MercaderData data = mercaderDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        return MercaderResponse.toDtoResponse(data);
    }

    @Override
    public GameDataTinyResponse getGameData() {
        MercaderData data = mercaderDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        return GameDataTinyResponse.toTinyResponse(data.getGameData());
    }

    @Override
    public void giveResources(GiveResourcesRequest request) {

        MercaderData data = mercaderDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        List<Long> reqCardIds = request.getResourceIds();
        List<Card> cardList = reqCardIds.stream()
                .flatMap(reqCardId -> data.getCards().stream()
                        .filter(mercaderCard -> Objects.equals(mercaderCard.getId(), reqCardId)))
                .collect(Collectors.toList());


        reqCardIds.removeAll(cardList.stream()
                .map(Card::getId)
                .collect(Collectors.toList()));

        if(!reqCardIds.isEmpty()){
            throw new CardNotFoundException();
        }

        PlayerData they = playerDataRepository.findById(request.getIdJugadorDestino())
                .orElseThrow(() -> new PlayerNotFoundException());

        cardList.forEach(c -> {
            data.getCards().remove(c);
            they.getCards().add(c);
        });
        playerDataRepository.save(data);
        playerDataRepository.save(they);
    }

    @Override
    public void playTradeRoutes(TradeRoutesRequest request) {

        MercaderData mercaderData = mercaderDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        Integer turno = mercaderData.getGameData().getTurno();

       for(SingleTradeRouteRequest singleTradeRouteRequest : request.getSingleTradeRouteRequests()){
            List<MarketCitySubregionRequest> route = singleTradeRouteRequest.getSubregions();
            route.sort(Comparator.comparing(MarketCitySubregionRequest::getPosition));
            validateRoute(route, route.get(0), 0, mercaderData);
        }

        mercaderData.setRoutes(savedRoutes(request, mercaderData));
        routeRepository.saveAll(mercaderData.getRoutes());
        playerDataRepository.save(mercaderData);
    }

    @Override
    public void buyResources(ResourceRequest request) {

        MercaderData mercaderData = mercaderDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        PersonalPricesEnum priceEnum = mercaderData.getPrices().stream()
                .filter(p -> Objects.equals(p.getId(), request.getPriceId()))
                .map(PersonalPrice::getName)
                .findAny()
                .orElseThrow(() -> new PriceNotFoundException());

        if (!paymentService.succesfulPay(mercaderData, request.getPayment(), priceEnum)) throw new PaymentNotPossibleException();

        mercaderData.getCards().add(ResourceCard.builder()
                        .resourceTypeEnum(ResourceTypeEnum.valueOf(priceEnum.name()))
                        .alreadyPlayed(false)
                        .build());

        mercaderDataRepository.save(mercaderData);
    }

    @Override
    public void upgradePrices(PricesRequest request) {

        Integer disminucionDePrecio = 0;
        MercaderData mercaderData = mercaderDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        if (!paymentService.succesfulPay(mercaderData, request.getPayment(), PersonalPricesEnum.TRADER_PRICES)) throw new PaymentNotPossibleException();

        for(PersonalPrice price : mercaderData.getPrices()){
            if(Objects.equals(request.getResourceType(), ResourceTypeEnum.TEXTIL.getId())){
                price.setTextil(price.getTextil()-disminucionDePrecio);
            }
            if(Objects.equals(request.getResourceType(), ResourceTypeEnum.AGROPECUARIA.getId())){
                price.setAgropecuaria(price.getAgropecuaria()-disminucionDePrecio);
            }
            if(Objects.equals(request.getResourceType(), ResourceTypeEnum.METALMECANICA.getId())){
                price.setMetalmecanica(price.getMetalmecanica()-disminucionDePrecio);
            }
            if(Objects.equals(request.getResourceType(), ResourceTypeEnum.CONSTRUCCION.getId())){
                price.setConstruccion(price.getConstruccion()-disminucionDePrecio);
            }
            if(Objects.equals(request.getResourceType(), ResourceTypeEnum.COMERCIAL.getId())){
                price.setComercial(price.getComercial()-disminucionDePrecio);
            }
        }
        mercaderDataRepository.save(mercaderData);
    }

    /*
                    MÉTODOS PRIVADOS
     */

    private void validateRoute(List<MarketCitySubregionRequest> route, MarketCitySubregionRequest subregion,
                                  Integer index, MercaderData mercaderData) {

        //En el caso de que el item no sea el último
        if(index < route.size()-1){

            SubRegionEnum currentSubregion = subregionRepository.findById(subregion.getId()).orElseThrow(() -> new SubRegionNotFoundException()).getSubRegionEnum();
            SubRegionEnum nextSubregion = subregionRepository.findById(route.get(subregion.getPosition().intValue()).getId()).orElseThrow(() -> new SubRegionNotFoundException()).getSubRegionEnum();

            //Si el siguiente no es adyacente
            if(!currentSubregion.getAdyacentes().contains(nextSubregion)){
                subregion.setSuccesfullyPlayed(false);
                return;

            }
            //Si el siguiente es adyacente
            //Si en la subregion actual hay una ciudad
            if(currentSubregion.getCity()!=null){
                Optional<Card> marketCard = cardRepository.findById(subregion.getCityMarketCardId());

                //Si el jugador no tiene la carta de ciudad
                if(!marketCard.isPresent() || !mercaderData.getCards().contains(marketCard.get())){
                    subregion.setSuccesfullyPlayed(false);
                    return;
                }
            }
            // Recursividad
            validateRoute(route, route.get(index+1), index+1, mercaderData);
            subregion.setSuccesfullyPlayed(true);
        } else {
            //En el caso de que el item sí sea el último
            Optional<Card> marketCard = cardRepository.findById(subregion.getCityMarketCardId());
            //Si el jugador tiene la carta de ciudad
            if(marketCard.isPresent() && mercaderData.getCards().contains(marketCard.get())){
                subregion.setSuccesfullyPlayed(true);
            } else {
                subregion.setSuccesfullyPlayed(false);
            }
        }
    }

    private List<Route> savedRoutes(TradeRoutesRequest request, MercaderData mercaderData) {

        return request.getSingleTradeRouteRequests().stream()
                .map(r -> {

                    List<GameSubRegion> subregions = subregionRepository.findAllById(r.getSubregions().stream().map(MarketCitySubregionRequest::getId).collect(Collectors.toList()));
                    Long provisionTradeScore = r.getSubregions().stream()
                            .filter(marketSubregion -> marketSubregion.getCityMarketCardId() != null)
                            .map(marketSubregion -> (MarketCard) cardRepository.findById(marketSubregion.getCityMarketCardId()).orElseThrow(() -> new CardNotFoundException()))
                            .mapToLong(MarketCard::getLevel)
                            .sum();

                    removeCards(r.getSubregions(), mercaderData);

                    return Route.builder()
                            .subregions(subregions)
                            .tradeScore(provisionTradeScore)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private void removeCards(List<MarketCitySubregionRequest> marketSubregion, MercaderData mercaderData) {

        List<Card> cards = cardRepository.findAllById(marketSubregion.stream()
                        .map(MarketCitySubregionRequest::getCityMarketCardId)
                        .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        cards.forEach(mercaderData.getCards()::remove);
    }

}
