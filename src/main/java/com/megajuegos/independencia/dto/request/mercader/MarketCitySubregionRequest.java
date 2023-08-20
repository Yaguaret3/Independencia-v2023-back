package com.megajuegos.independencia.dto.request.mercader;

import lombok.Data;

@Data
public class MarketCitySubregionRequest {

    private Long id;
    private Long cityMarketCardId;
    private Long position;
    private boolean succesfullyPlayed;
}
