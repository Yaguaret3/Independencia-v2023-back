package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.City;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CityTinyResponse {

    private Long id;
    private String name;
    private Long gobernadorId;
    private String gobernadorName;
    private String subRegion;
    private List<String> buildings;
    private Integer prestige;

    public static CityTinyResponse toTinyResponse(City entity){
        return CityTinyResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .gobernadorId(entity.getGobernadorData()==null?null:entity.getGobernadorData().getId())
                .gobernadorName(entity.getGobernadorData() != null ?
                        entity.getGobernadorData().getUser().getUsername() :
                        "")
                .subRegion(entity.getSubRegion().getSubRegionEnum().name())
                .buildings(entity.getBuildings().stream().map(b -> b.getBuildingType().name()).collect(Collectors.toList()))
                .prestige(entity.getPrestige())
                .build();
    }
}
