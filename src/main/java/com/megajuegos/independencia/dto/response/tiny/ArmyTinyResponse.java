package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.Army;
import com.megajuegos.independencia.entities.Battle;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ArmyTinyResponse {

    private Long armyId;
    private Long capitanId;
    private Long gameAreaId;
    private List<Long> battles;
    private Integer size;

    public static ArmyTinyResponse toTinyResponse(Army entity){

        return ArmyTinyResponse.builder()
                .armyId(entity.getId())
                .capitanId(entity.getCapitanData().getId())
                .gameAreaId(entity.getGameSubRegion().getId())
                .battles(entity.getBatallas()
                        .stream()
                        .map(Battle::getId)
                        .collect(Collectors.toList()))
                .size(entity.getCantidad())
                .build();
    }
}
