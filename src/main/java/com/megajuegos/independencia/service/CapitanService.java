package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.response.CapitanResponse;
import com.megajuegos.independencia.dto.request.capitan.*;

import javax.management.InstanceNotFoundException;

public interface CapitanService {

    CapitanResponse getData();
    void buyActionCards(BuyRequest request) throws InstanceNotFoundException;
    void buyBattleCards(BuyRequest request) throws InstanceNotFoundException;
    void playActionRequest(ActionRequest request);
    void playBattleCards(BattleRequest request);
    void makeCamp(CampRequest request);

    void move(MovementRequest request);
}
