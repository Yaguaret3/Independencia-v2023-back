package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.request.control.*;
import com.megajuegos.independencia.dto.response.ControlResponse;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;

import javax.management.InstanceNotFoundException;
import java.util.Map;

public interface ControlService {

    String createAndGiveResourceCard(Long playerDataId, NewResourceCardRequest request);

    String moveCard(Long from, Long to, Long carta);

    String removeCard(Long idCard);

    GameDataFullResponse getFullData();

    String solveBattle(SolveBattleRequest request);

    String createAndGiveRepresentationCard(Long playerDataId, NewRepresentationCardRequest request) throws InstanceNotFoundException;

    String createAndGiveMarketCard(Long playerId, NewMarketCardRequest request);

    String createAndAssignPersonalPrice(Long playerDataId);

    String assignCongressPresident(Long revolucionarioId);

    String concludePhase();

    String createAndGiveExtraCard(Long playerId, ExtraCardRequest request);

    String editCity(Map<String, Integer> request, Long id);

    String removeBuilding(Long cityId, Long buildingId);

    String addBuilding(Long cityId, NewBuildingRequest request);

    String updateRoute(Long routeId, AssignRouteValueRequest request);

    String updatePrices(Long priceId, Map<String, Integer> request);

    String updateTradeScore(Long playerId, SoleValueRequest request);

    String updateVotation(Long votationId, UpdateVotationRequest request);

    String addVote(Long votationId, NewVoteRequest request);

    String updateVote(Long voteId, UpdateVoteRequest request);

    String createBattle(CreateBattleRequest request);

    String assignRandomValuesBattle(Long battleId);

    String assignReserve(Long playerId, Integer militia);

    String deleteArmy(Long armyId);

    String createArmy(NewArmyRequest request);

    String moveCamp(MoveCampRequest request);

    String assignNewDiputadoToCity(Long cityId, Long diputadoId) throws InstanceNotFoundException;

    String addPlata(Long playerId, Integer plata);

    String removeCongress(Long congresoId);

    String updateCongress(Long congresoId, UpdateCongressRequest request);

    String createNewCongress(CreateNewCongressRequest request);

    ControlResponse getControlData();

    String moveToCongress(MoveToCongressRequest request);

    String createAndGiveActionCard(Long playerId, NewActionCardRequest request);

    String createAndGiveBattleCard(Long playerId, NewBattleCardRequest request);


}

