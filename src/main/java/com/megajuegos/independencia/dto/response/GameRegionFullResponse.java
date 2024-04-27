package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.GameRegion;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class GameRegionFullResponse {

    private Long id;
    private List<GameSubRegionFullResponse> subregions;

    public static GameRegionFullResponse toFullResponse(GameRegion entity){
        return GameRegionFullResponse.builder()
                .id(entity.getId())
                .subregions(entity.getSubRegions()
                        .stream()
                        .map(GameSubRegionFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
