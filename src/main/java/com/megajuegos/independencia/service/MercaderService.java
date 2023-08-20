package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.mercader.GiveResourcesRequest;
import com.megajuegos.independencia.dto.request.mercader.PricesRequest;
import com.megajuegos.independencia.dto.request.mercader.ResourceRequest;
import com.megajuegos.independencia.dto.request.mercader.TradeRoutesRequest;
import com.megajuegos.independencia.dto.response.MercaderResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;

import javax.management.InstanceNotFoundException;

public interface MercaderService {

    MercaderResponse getData();
    GameDataTinyResponse getGameData();
    void giveResources(GiveResourcesRequest request);
    void playTradeRoutes(TradeRoutesRequest request) throws InstanceNotFoundException;
    void buyResources(ResourceRequest request);
    void upgradePrices(PricesRequest request) throws InstanceNotFoundException;
}
