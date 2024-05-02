package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Camp;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CampResponse {

    private Long id;
    private Long capitanId;
    private Integer nivel;
    private Long gameSubregionId;
    private String gameSubregionName;
    private Long gameRegionId;
    private String gameRegionName;

    public static CampResponse toDtoResponse(Camp entity){
        return CampResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanData().getId())
                .nivel(entity.getNivel())
                .gameSubregionId(entity.getSubregion().getId())
                .gameSubregionName(entity.getSubregion().getNombre())
                .gameRegionId(entity.getGameRegion().getId())
                .gameRegionName(entity.getGameRegion().getRegionEnum().getNombre())
                .build();
    }
}
