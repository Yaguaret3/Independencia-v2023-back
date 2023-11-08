package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.control.ExtraCardRequest;
import com.megajuegos.independencia.dto.request.control.NewBuildingRequest;
import com.megajuegos.independencia.dto.request.control.NewMarketCardRequest;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;

import javax.management.InstanceNotFoundException;
import java.util.Map;

public interface ControlService {

    String createAndGiveResourceCard(Long playerDataId);
    String moveCard(Long from, Long to, Long carta);
    String removeCard(Long idCard);
    GameDataFullResponse getFullData();
    String solveBattle(Long battle);
    String createAndGiveRepresentationCard(Long playerDataId) throws InstanceNotFoundException;
    String createAndGiveMarketCard(NewMarketCardRequest request);
    String createAndAssignPersonalPrice(Long playerDataId);
    String assignCongressPresident(Long revolucionarioId);

    void concludePhase();

    String createAndGiveExtraCard(ExtraCardRequest request, Long playerDataId);

    String editCity(Map<String, String> request, Long id);

    String removeBuilding(Long cityId, Long buildingId);

    String addBuilding(Long cityId, NewBuildingRequest request);
}

