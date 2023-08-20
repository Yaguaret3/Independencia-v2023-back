package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.GameRegion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class GameRegionTinyGobernadorResponse {

    private Long id;
    private String nombre;
    private String color;
    private String area;
    private String bgTop;
    private String bgLeft;
    private String bgScale;
    private String fgTop;
    private String fgLeft;
    private String fgViewBox;
    private String fgHeight;
    private String fgWidth;
    private List<GameSubRegionTinyResponse> subRegions;

    public static GameRegionTinyGobernadorResponse toTinyResponse(GameRegion entity){
        return GameRegionTinyGobernadorResponse.builder()
                .id(entity.getId())
                .nombre(entity.getRegionEnum().getNombre())
                .color(entity.getRegionEnum().getColor())
                .area(entity.getRegionEnum().getFgArea())
                .bgTop(entity.getRegionEnum().getBgTopGobernador())
                .bgLeft(entity.getRegionEnum().getBgLeftGobernador())
                .bgScale(entity.getRegionEnum().getBgScaleGobernador())
                .fgTop(entity.getRegionEnum().getFgTopGobernador())
                .fgLeft(entity.getRegionEnum().getFgLeftGobernador())
                .fgViewBox(entity.getRegionEnum().getFgViewBox())
                .fgHeight(entity.getRegionEnum().getFgHeightGobernador())
                .fgWidth(entity.getRegionEnum().getFgWidthGobernador())
                .subRegions(entity.getSubRegions()
                        .stream()
                        .map(GameSubRegionTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
