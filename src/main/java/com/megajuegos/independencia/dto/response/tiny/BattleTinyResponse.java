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
    private List<ArmyTinyResponse> combatientes;
    private Long gameAreaId;
    private Integer turn;
    private Boolean active;

    public static BattleTinyResponse toTinyResponse(Battle entity){

        return BattleTinyResponse.builder()
                .id(entity.getId())
                .combatientes(entity.getCombatientes().stream()
                        .map(ArmyTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .gameAreaId(entity.getSubregion().getId())
                .turn(entity.getTurnoDeJuego())
                .active(entity.getActive())
                .build();
    }
}
