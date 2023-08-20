package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Building;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponse {

    private Long id;
    private String buildingType;
    private String bonification;
    private String city;

    public BuildingResponse toDtoResponse(Building entity){
        return BuildingResponse.builder()
                .id(entity.getId())
                .buildingType(entity.getBuildingType().name())
                .bonification(entity.getBuildingType().getBonificacion())
                .city(entity.getCity().getName())
                .build();
    }
}
