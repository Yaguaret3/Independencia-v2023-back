package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.response.GameDataFullResponse;

import javax.management.InstanceNotFoundException;

public interface ControlService {

    String advanceTurn(Long gameDataId);
    String createAndGiveResourceCard(Long playerDataId);
    String moveCard(Long from, Long to, Long carta);
    String removeCard(Long idCard);
    GameDataFullResponse getFullData();
    String solveBattle(Long battle);
    String createAndGiveRepresentationCard(Long playerDataId) throws InstanceNotFoundException;
    String createAndGiveMarketCard(Long playerDataId);
    String createAndAssignPersonalPrice(Long playerDataId);
    String assignCongressPresident(Long revolucionarioId);
}

