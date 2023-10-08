package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.GameRegion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameRegionVeryTinyResponse {

    private Long id;
    private String nombre;

    public static GameRegionVeryTinyResponse toVeryTinyDto(GameRegion entity){
        return GameRegionVeryTinyResponse.builder()
                .id(entity.getId())
                .nombre(entity.getRegionEnum().getNombre())
                .build();
    }
}
