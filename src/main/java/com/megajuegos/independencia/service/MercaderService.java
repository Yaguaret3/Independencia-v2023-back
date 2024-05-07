package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.mercader.*;
import com.megajuegos.independencia.dto.response.MercaderResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;

import javax.management.InstanceNotFoundException;

public interface MercaderService {

    MercaderResponse getData();
    GameDataTinyResponse getGameData();
    void playTradeRoutes(SingleTradeRouteRequest request);
    void buyResources(ResourceRequest request);
    void upgradePrices(PricesRequest request) throws InstanceNotFoundException;
}
