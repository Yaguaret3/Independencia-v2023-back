package com.megajuegos.independencia.dto.request.mercader;

import lombok.Data;

import java.util.List;

@Data
public class SingleTradeRouteRequest {

    private List<MarketCitySubregionRequest> subregions;
}
