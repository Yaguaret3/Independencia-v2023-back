package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.GameRegion;
import com.megajuegos.independencia.entities.GameSubRegion;
import com.megajuegos.independencia.entities.data.MercaderData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class GameRegionTinyResponse {

    private Long id;
    private String nombre;
    private String area;
    private String bgTop;
    private String bgLeft;
    private String bgScale;
    private String fgTop;
    private String fgLeft;
    private String fgViewBox;
    private String fgHeight;
    private String fgWidth;
    private String color;
    private List<GameSubRegionTinyResponse> subRegions;

    public static GameRegionTinyResponse toTinyResponse(GameRegion entity){
        return GameRegionTinyResponse.builder()
                .id(entity.getId())
                .nombre(entity.getRegionEnum().getNombre())
                .area(entity.getRegionEnum().getFgArea())
                .bgTop(entity.getRegionEnum().getBgTop())
                .bgLeft(entity.getRegionEnum().getBgLeft())
                .bgScale(entity.getRegionEnum().getBgScale())
                .fgTop(entity.getRegionEnum().getFgTop())
                .fgLeft(entity.getRegionEnum().getFgLeft())
                .fgViewBox(entity.getRegionEnum().getFgViewBox())
                .fgHeight(entity.getRegionEnum().getFgHeight())
                .fgWidth(entity.getRegionEnum().getFgWidth())
                .color(entity.getRegionEnum().getColor())
                .subRegions(entity.getSubRegions()
                        .stream()
                        .map(GameSubRegionTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
