package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.MarketCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCardResponse {

    private Long id;
    private Integer level;
    private String cityName;

    public static MarketCardResponse toDtoResponse(MarketCard entity){
        return MarketCardResponse.builder()
                .id(entity.getId())
                .cityName(entity.getNombreCiudad())
                .level(entity.getLevel())
                .build();
    }
}
