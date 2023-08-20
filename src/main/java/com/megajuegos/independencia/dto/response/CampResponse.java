package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Camp;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CampResponse {

    private Long id;
    private Long capitanId;
    private Integer nivel;
    private Long areaDeJuegoId;

    public static CampResponse toDtoResponse(Camp entity){
        return CampResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanData().getId())
                .nivel(entity.getNivel())
                .areaDeJuegoId(entity.getGameSubRegion().getId())
                .build();
    }
}
