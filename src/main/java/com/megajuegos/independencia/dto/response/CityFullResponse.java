package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityFullResponse {

    private Long id;
    private String name;
    private String diputado;
    private Integer marketLevel;
    private Integer publicOpinion;
    private Integer taxesLevel;
    private Integer prestige;
    private Long gobernadorId;
    private List<BuildingResponse> buildings;

    public static CityFullResponse toDtoResponse(City entity){
        return CityFullResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .diputado(entity.getDiputado())
                .marketLevel(entity.getMarketLevel())
                .publicOpinion(entity.getPublicOpinion())
                .taxesLevel(entity.getTaxesLevel())
                .prestige(entity.getPrestige())
                .gobernadorId(entity.getGobernadorData().getId())
                .buildings(entity.getBuildings().stream()
                        .map(edificio -> new BuildingResponse().toDtoResponse(edificio))
                        .collect(Collectors.toList()))
                .build();
    }
}
