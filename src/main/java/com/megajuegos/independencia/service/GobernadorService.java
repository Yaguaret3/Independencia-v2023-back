package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.gobernador.*;
import com.megajuegos.independencia.dto.response.GobernadorResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyGobernadorResponse;
import com.megajuegos.independencia.service.util.PaymentRequestUtil;

import javax.management.InstanceNotFoundException;

public interface GobernadorService {

    GobernadorResponse getData();
    GameDataTinyGobernadorResponse getGameData();
    void sellMarketPlace(MarketPlaceSellRequest request);
    void changeTaxes(TaxesRequest request);
    void buildNewBuilding(UpgradeBuildingRequest request) throws InstanceNotFoundException;
    void giveRepresentationRequest(RepresentationRequest request);
    void promoteCorruption(CorruptionRequest request);
    void recruitMilitia(PaymentRequestUtil request);
    void assignMilitia(AssignMilitiaRequest request);
    void financeCongress(FinanceCongressRequest request);
    void upgradeMarketplace(PaymentRequestUtil request);
}
