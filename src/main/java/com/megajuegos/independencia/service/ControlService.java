package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.control.*;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;

import javax.management.InstanceNotFoundException;
import java.util.Map;

public interface ControlService {

    String createAndGiveResourceCard(Long playerDataId);
    String moveCard(Long from, Long to, Long carta);
    String removeCard(Long idCard);
    GameDataFullResponse getFullData();
    String solveBattle(SolveBattleRequest request);
    String createAndGiveRepresentationCard(Long playerDataId) throws InstanceNotFoundException;
    String createAndGiveMarketCard(NewMarketCardRequest request);
    String createAndAssignPersonalPrice(Long playerDataId);
    String assignCongressPresident(Long revolucionarioId);

    void concludePhase();

    String createAndGiveExtraCard(ExtraCardRequest request, Long playerDataId);

    String editCity(Map<String, String> request, Long id);

    String removeBuilding(Long cityId, Long buildingId);

    String addBuilding(Long cityId, NewBuildingRequest request);

    String assignFinalRouteValue(Long routeId, AssignRouteValueRequest request);

    String updatePrices(Long priceId, Map<String, Integer> request);

    String updateVotation(Long votationId, UpdateVotationRequest request);

    String addVote(Long votationId, NewVoteRequest request);

    String updateVote(Long voteId, UpdateVoteRequest request);

    String createBattle(CreateBattleRequest request);

    String asignRandomValuesBattle(Long battleId);

    String assignMilitia(Long armyId, Integer militia);

    String assignReserve(Long capitanId, Integer militia);

    String deleteArmy(Long armyId);

    String createArmy(NewArmyRequest request);

    String moveCamp(MoveCampRequest request);

    String assignMilitiaToGobernador(Long gobernadorId, Integer militia);

    String assignNewDiputadoToCity(Long cityId, Long diputadoId) throws InstanceNotFoundException;

    String addPlata(Long playerId, Integer plata);
}

