package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Action;
import com.megajuegos.independencia.entities.GameRegion;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class GameRegionFullResponse {

    private Long id;
    private String name;
    private List<GameSubRegionFullResponse> subregions;
    private List<ActionResponse> accionesMilitares;

    public static GameRegionFullResponse toFullResponse(GameRegion entity){
        return GameRegionFullResponse.builder()
                .id(entity.getId())
                .name(entity.getRegionEnum().getNombre())
                .subregions(entity.getSubRegions()
                        .stream()
                        .map(GameSubRegionFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .accionesMilitares(entity.getDefenseActions().stream()
                        .filter(a -> !a.isSolved())
                        .map(ActionResponse::toDto)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
