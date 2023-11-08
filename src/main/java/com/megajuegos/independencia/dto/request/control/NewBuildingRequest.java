package com.megajuegos.independencia.dto.request.control;

import com.megajuegos.independencia.enums.BuildingTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewBuildingRequest {

    private BuildingTypeEnum buildingType;
}
