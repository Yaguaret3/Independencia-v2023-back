package com.megajuegos.independencia.dto.request.control;

import lombok.Data;

@Data
public class NewMarketCardRequest {

    private Long playerId;
    private String cityName;
    private Integer level;
}
