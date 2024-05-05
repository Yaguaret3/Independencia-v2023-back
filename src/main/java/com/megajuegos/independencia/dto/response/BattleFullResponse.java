package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Battle;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class BattleFullResponse {

    private Long id;
    private Long subregionId;
    private String subregionName;
    private Long regionId;
    private String regionName;
    private List<BattleCardFullResponse> cartasDeCombate;
    private List<ArmyFullResponse> combatientes;
    private Integer turnoDeJuego;
    private Boolean active;

    public static BattleFullResponse toFullResponse(Battle entity){

        return BattleFullResponse.builder()
                .id(entity.getId())
                .subregionId(entity.getSubregion().getId())
                .subregionName(entity.getSubregion().getNombre())
                .regionId(entity.getSubregion().getGameRegion().getId())
                .regionName(entity.getSubregion().getGameRegion().getRegionEnum().getNombre())
                .combatientes(entity.getCombatientes().stream()
                        .map(ArmyFullResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .turnoDeJuego(entity.getTurnoDeJuego())
                .active(entity.getActive())
                .build();
    }
}
