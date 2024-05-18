package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.tiny.GameRegionTinyResponse;
import com.megajuegos.independencia.dto.response.util.PlayerDataCardsUtil;
import com.megajuegos.independencia.entities.GameRegion;
import com.megajuegos.independencia.entities.data.GobernadorData;
import com.megajuegos.independencia.enums.LogTypeEnum;
import com.megajuegos.independencia.enums.ResourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GobernadorResponse {

    private Long id;
    private CityFullResponse city;
    private GameRegionTinyResponse gameRegion;
    private Integer plata;
    private Integer milicia;
    private RepresentationCardResponse representacion;
    private List<MarketCardResponse> mercados;
    private List<ResourceCardResponse> recursos;
    private List<ExtraCardResponse> extras;
    private List<ResourceTypeEnum> tiposDeRecurso;
    private GobernadorPricesResponse precios;
    private List<LogResponse> historial;

    public static GobernadorResponse toDtoResponse(GobernadorData entity, GameRegion gameRegion) {

        PlayerDataCardsUtil util = new PlayerDataCardsUtil(entity);
        RepresentationCardResponse representationUtil = util.getRepresentationCardList().isEmpty() ? null :
                RepresentationCardResponse.toDtoResponse(util.getRepresentationCardList().get(0));

        List<LogResponse> historial = entity.getLogs().stream()
                .map(LogResponse::toResponse)
                .collect(Collectors.toList());

        Collections.reverse(historial);

        return GobernadorResponse.builder()
                .id(entity.getId())
                .city(CityFullResponse.toDtoResponse(entity.getCity()))
                .gameRegion(GameRegionTinyResponse.toTinyResponse(gameRegion))
                .plata(entity.getPlata())
                .milicia(entity.getMilicia())
                .representacion(representationUtil)
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
                .tiposDeRecurso(Arrays.stream(ResourceTypeEnum.values()).collect(Collectors.toList()))
                .precios(GobernadorPricesResponse.toDto(entity.getPrices()))
                .historial(historial)
                .build();
    }
}
