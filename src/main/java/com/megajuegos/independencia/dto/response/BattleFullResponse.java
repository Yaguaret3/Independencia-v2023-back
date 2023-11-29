package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.tiny.BattleCardTinyResponse;
import com.megajuegos.independencia.entities.Battle;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class BattleFullResponse {

    private Long id;
    private List<BattleCardFullResponse> cartasDeCombate;
    private List<ArmyResponse> combatientes;
    private Integer turnoDeJuego;
    private Boolean active;

    public static BattleFullResponse toFullResponse(Battle entity){

        return BattleFullResponse.builder()
                .id(entity.getId())
                .combatientes(entity.getCombatientes().stream()
                        .map(ArmyResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .turnoDeJuego(entity.getTurnoDeJuego())
                .active(entity.getActive())
                .build();
    }
}
