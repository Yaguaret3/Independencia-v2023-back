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
    private String nombre;
    private List<GameSubRegionFullResponse> subregions;
    private List<ActionResponse> accionesMilitares;


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

    public static GameRegionFullResponse toFullResponse(GameRegion entity){
        return GameRegionFullResponse.builder()
                .id(entity.getId())
                .nombre(entity.getRegionEnum().getNombre())
                .subregions(entity.getSubRegions()
                        .stream()
                        .map(GameSubRegionFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .accionesMilitares(entity.getDefenseActions().stream()
                        .filter(a -> !a.isSolved())
                        .map(ActionResponse::toDto)
                        .collect(Collectors.toList())
                )

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

                .build();
    }
}
