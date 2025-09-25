package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.tiny.GameSubRegionTinyResponse;
import com.megajuegos.independencia.entities.Route;
import com.megajuegos.independencia.entities.card.MarketCard;
import lombok.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class RouteResponse {

    private Long id;
    private List<GameSubRegionTinyResponse> subregions;
    private Long tradeScore;
    private Integer turn;
    private String comentario;
    private String mercaderName;

    public static RouteResponse toDto(Route entity){

        return RouteResponse.builder()
                .id(entity.getId())
                .tradeScore(entity.getTradeScore())
                .turn(entity.getTurn())
                .comentario(entity.getComentario())
                .subregions(entity.getSubregions().stream()
                        .map(GameSubRegionTinyResponse::toTinyResponse)
                        .map(subregion -> {
                            Optional<MarketCard> card = entity.getMarkets().stream().filter(c -> c.getNombreCiudad().equals(subregion.getNombre())).findFirst();
                            if(card.isPresent()){
                                return subregion.addTradeScore(card.get().getLevel());
                            }
                            return subregion;
                        })
                        .collect(Collectors.toList()))
                .mercaderName(entity.getMercader().getUser().getUsername())
                .build();
    }
}
