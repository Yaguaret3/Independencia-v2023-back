package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Army;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ArmyResponse {

    private Long id;
    private Long capitanId;
    private String capitanName;
    private Long gameSubRegion;
    public static ArmyResponse toDtoResponse(Army entity){

        return ArmyResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanData().getId())
                .capitanName(entity.getCapitanData().getUsername())
                .gameSubRegion(entity.getGameSubRegion().getId())
                .build();
    }

}
