package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.Battle;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class BattleTinyResponse {

    private Long id;
    private List<BattleCardTinyResponse> battleCards;
    private List<ArmyTinyResponse> armies;
    private Long gameAreaId;
    private Integer turn;
    private Boolean active;

    public static BattleTinyResponse toTinyResponse(Battle entity){

        return BattleTinyResponse.builder()
                .id(entity.getId())
                .battleCards(entity.getCartasDeCombate()
                        .stream()
                        .map(BattleCardTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .armies(entity.getEjercitos()
                        .stream()
                        .map(ArmyTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .gameAreaId(entity.getGameSubRegion().getId())
                .turn(entity.getTurnoDeJuego())
                .active(entity.getActive())
                .build();
    }
}
