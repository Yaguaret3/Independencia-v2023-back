package com.megajuegos.independencia.dto.request.mercader;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class MarketCitySubregionRequest {

    private Long id;
    @Nullable
    private Long cityMarketCardId;
    private Long position;
    private boolean succesfullyPlayed;
}
