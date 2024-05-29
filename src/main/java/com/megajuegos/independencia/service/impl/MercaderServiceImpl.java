package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.mercader.*;
import com.megajuegos.independencia.dto.response.MercaderResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.entities.GameSubRegion;
import com.megajuegos.independencia.entities.Log;
import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.Route;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.MarketCard;
import com.megajuegos.independencia.entities.card.ResourceCard;
import com.megajuegos.independencia.entities.data.MercaderData;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.*;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.MercaderDataRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.MercaderService;
import com.megajuegos.independencia.service.PaymentService;
import com.megajuegos.independencia.service.util.GameIdUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MercaderServiceImpl implements MercaderService {

    private final CardRepository cardRepository;
    private final PaymentService paymentService;
    private final UserUtil userUtil;
    private final MercaderDataRepository mercaderDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final RouteRepository routeRepository;
    private final GameSubRegionRepository subregionRepository;
    private final GameIdUtil gameIdUtil;
    private final LogRepository logRepository;

    @Override
    public MercaderResponse getData() {
        MercaderData mercaderData = getPlayerData();
        return MercaderResponse.toDtoResponse(mercaderData);
    }

    @Override
    public GameDataTinyResponse getGameData() {
        MercaderData mercaderData = getPlayerData();

        GameDataTinyResponse response = GameDataTinyResponse.toTinyResponse(mercaderData.getGameData());
        /*
        if(response.getFase() == PhaseEnum.REVEALING){
            response.getGameRegions()
                    .forEach(r -> r.getSubregions()
                        .forEach(s -> s.reveal(         // AQUÍ ESTÁ EL REVEAL
                                mercaderData.getGameData().getPlayers().stream()
                                        .filter(MercaderData.class::isInstance)
                                        .map(p -> (MercaderData) p)
                                        .filter(m -> m.getRoutes().stream()
                                                .filter(ro -> Objects.equals(ro.getTurn(), response.getTurno()))
                                                .anyMatch(ro -> ro.getSubregions().stream()
                                                        .anyMatch(su -> su.getNombre().equals(s.getNombre()))))
                                        .collect(Collectors.toList())
                        )));
        }

         */
        return response;
    }

    // Validamos que sean adyacentes
    // Validamos que las ciudades tengan carta de ciudad
    // Asignamos bien jugado
    // Contamos provisoriamente el valor de las ciudades
    // Dejamos del lado de Control asignar el valor final
    @Override
    public void playTradeRoutes(SingleTradeRouteRequest request) {

        MercaderData mercaderData = getPlayerData();

        List<MarketCitySubregionRequest> roads = request.getSubregions();
        roads.sort(Comparator.comparing(MarketCitySubregionRequest::getPosition));
        validateRoute(roads, roads.get(0), 0, mercaderData);

        Route route = savedRoute(request, mercaderData);
        routeRepository.save(route);

        mercaderData.getRoutes().add(route);
        playerDataRepository.save(mercaderData);

        Log log = Log.builder()
                .turno(mercaderData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Creaste una ruta comercial que atraviesa %s subregiones y %s ciudades",
                        route.getSubregions().size(),
                        route.getSubregions().stream().filter(s -> s.getCity() != null).count()))
                .player(mercaderData)
                .build();

        logRepository.save(log);
    }

    @Override
    public void buyResources(ResourceRequest request) {

        MercaderData mercaderData = getPlayerData();

        PersonalPricesEnum priceEnum = mercaderData.getPrices().stream()
                .filter(p -> Objects.equals(p.getId(), request.getPriceId()))
                .map(PersonalPrice::getName)
                .findAny()
                .orElseThrow(() -> new PriceNotFoundException());

        if (Boolean.FALSE.equals(paymentService.succesfulPay(mercaderData, request.getPayment(), priceEnum))) throw new PaymentNotPossibleException();

        Card card = ResourceCard.builder()
                .resourceTypeEnum(ResourceTypeEnum.valueOf(priceEnum.name()))
                .alreadyPlayed(false)
                .playerData(mercaderData)
                .build();

        cardRepository.save(card);

        Log log = Log.builder()
                .turno(mercaderData.getGameData().getTurno())
                .tipo(LogTypeEnum.RECIBIDO)
                .nota(String.format("Compraste un recurso de: %s", ResourceTypeEnum.valueOf(priceEnum.name()).name()))
                .player(mercaderData)
                .build();

        logRepository.save(log);
    }

    @Override
    public void upgradePrices(PricesRequest request) {

        Integer disminucionDePrecio = 0;
        MercaderData mercaderData = getPlayerData();

        if (Boolean.FALSE.equals(paymentService.succesfulPay(mercaderData, request.getPayment(), PersonalPricesEnum.TRADER_PRICES))) throw new PaymentNotPossibleException();

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
        Log log = Log.builder()
                .turno(mercaderData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota("Mejoraste la productividad y bajaste los costos")
                .player(mercaderData)
                .build();

        logRepository.save(log);
    }

    /*
                    MÉTODOS PRIVADOS
     */

    private void validateRoute(List<MarketCitySubregionRequest> route, MarketCitySubregionRequest subregion,
                                  Integer index, MercaderData mercaderData) {

        //En el caso de que el item no sea el último
        if(index < route.size()-1){

            SubRegionEnum currentSubregion = subregionRepository.findById(subregion.getId()).orElseThrow(() -> new SubRegionNotFoundException(subregion.getId())).getSubRegionEnum();
            SubRegionEnum nextSubregion = subregionRepository.findById(route.get(subregion.getPosition().intValue()).getId()).orElseThrow(() -> new SubRegionNotFoundException(route.get(subregion.getPosition().intValue()).getId())).getSubRegionEnum();

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
            subregion.setSuccesfullyPlayed(marketCard.isPresent() && mercaderData.getCards().contains(marketCard.get()));
        }
    }

    private Route savedRoute(SingleTradeRouteRequest request, MercaderData mercaderData) {

        List<GameSubRegion> subregions = subregionRepository.findAllById(request.getSubregions().stream().map(MarketCitySubregionRequest::getId).collect(Collectors.toList()));
        Long provisionTradeScore = request.getSubregions().stream()
                .filter(marketSubregion -> marketSubregion.getCityMarketCardId() != null)
                .map(marketSubregion -> (MarketCard) cardRepository.findById(marketSubregion.getCityMarketCardId()).orElseThrow(() -> new CardNotFoundException(marketSubregion.getCityMarketCardId())))
                .mapToLong(MarketCard::getLevel)
                .sum();

        removeCards(request.getSubregions(), mercaderData);

        return Route.builder()
                .subregions(subregions)
                .tradeScore(provisionTradeScore)
                .turn(mercaderData.getGameData().getTurno())
                .mercader(mercaderData)
                .build();
    }

    private void removeCards(List<MarketCitySubregionRequest> marketSubregion, MercaderData mercaderData) {

        List<Card> cards = cardRepository.findAllById(marketSubregion.stream()
                        .map(MarketCitySubregionRequest::getCityMarketCardId)
                        .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        cards.forEach(mercaderData.getCards()::remove);
        cardRepository.deleteAll(cards);
    }
    private MercaderData getPlayerData(){
        Long playerId = userUtil.getCurrentUser().getPlayerDataList().stream()
                .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                .findFirst()
                .map(PlayerData::getId)
                .orElseThrow(PlayerNotFoundException::new);
        return mercaderDataRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
}
