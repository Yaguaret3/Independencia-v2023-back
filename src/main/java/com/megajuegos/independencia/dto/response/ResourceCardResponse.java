package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.ResourceCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceCardResponse {

    Long id;
    String resourceTypeEnum;

    public static ResourceCardResponse toDtoResponse(ResourceCard entity){

        return ResourceCardResponse.builder()
                .id(entity.getId())
                .resourceTypeEnum(entity.getResourceTypeEnum().name())
                .build();
    }
}
