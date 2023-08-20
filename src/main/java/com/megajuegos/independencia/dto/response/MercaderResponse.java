package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.util.PlayerDataCardsUtil;
import com.megajuegos.independencia.entities.data.MercaderData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class MercaderResponse {

    private Long id;
    private List<PersonalPriceResponse> preciosDeRecursos;
    private Integer puntajeComercial;
    private Integer puntajeComercialAcumulado;
    private List<MarketCardResponse> mercados;
    private List<ResourceCardResponse> recursos;
    private List<ExtraCardResponse> extras;

    public static MercaderResponse toDtoResponse(MercaderData entity){

        PlayerDataCardsUtil util = new PlayerDataCardsUtil(entity);

        return MercaderResponse.builder()
                .id(entity.getId())
                .preciosDeRecursos(entity.getPrices()
                        .stream()
                        .map(PersonalPriceResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .puntajeComercial(entity.getPuntajeComercial())
                .puntajeComercialAcumulado(entity.getPuntajeComercialAcumulado())
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
                .build();
    }
}
