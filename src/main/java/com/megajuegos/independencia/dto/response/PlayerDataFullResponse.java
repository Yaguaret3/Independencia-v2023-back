package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.util.PlayerDataCardsUtil;
import com.megajuegos.independencia.entities.data.PlayerData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PlayerDataFullResponse {

    private Long id;
    private String username;
    private List<MarketCardResponse> mercados;
    private List<ResourceCardResponse> recursos;
    private List<ExtraCardResponse> extras;
    private List<RepresentationCardResponse> representacion;
    private List<ActionCardResponse> actionCards;
    private List<BattleCardFullResponse> battleCards;
    private List<PersonalPriceResponse> prices;

    public static PlayerDataFullResponse toFullResponse(PlayerData entity){

        PlayerDataCardsUtil util = new PlayerDataCardsUtil(entity);

        return PlayerDataFullResponse.builder()
                .id(entity.getId())
                .mercados(util.getMarketCardList()
                        .stream()
                        .map(MarketCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .recursos(util.getResourceCardList()
                        .stream()
                        .map(ResourceCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .extras(util.getExtraCardList()
                        .stream()
                        .map(ExtraCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .representacion(util.getRepresentationCardList()
                        .stream()
                        .map(RepresentationCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .actionCards(util.getActionCardList()
                        .stream()
                        .map(ActionCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .battleCards(util.getBattleCardList()
                        .stream()
                        .map(BattleCardFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .prices(entity.getPrices().stream()
                        .map(PersonalPriceResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
