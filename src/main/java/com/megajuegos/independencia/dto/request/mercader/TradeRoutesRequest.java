package com.megajuegos.independencia.dto.request.mercader;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TradeRoutesRequest {

    List<SingleTradeRouteRequest> singleTradeRouteRequests;
}
