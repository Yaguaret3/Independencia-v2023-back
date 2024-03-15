package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.tiny.GameSubRegionTinyResponse;
import com.megajuegos.independencia.entities.Route;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class RouteResponse {

    private Long id;
    private List<GameSubRegionTinyResponse> subregions;
    private Long tradeScore;
    private Integer turn;
    private String comentario;

    public static RouteResponse toDto(Route entity){

        return RouteResponse.builder()
                .id(entity.getId())
                .tradeScore(entity.getTradeScore())
                .turn(entity.getTurn())
                .comentario(entity.getComentario())
                .subregions(entity.getSubregions().stream().map(GameSubRegionTinyResponse::toTinyResponse).collect(Collectors.toList()))
                .build();
    }
}
