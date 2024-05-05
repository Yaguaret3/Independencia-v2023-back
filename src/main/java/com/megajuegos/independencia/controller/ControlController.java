package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.control.*;
import com.megajuegos.independencia.dto.response.ControlResponse;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;
import com.megajuegos.independencia.service.ControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/api/control")
@RequiredArgsConstructor
public class ControlController {

    private final ControlService service;

    // INIT AND TEST

    @PostMapping("/{playerId}/create-give-resource-card")
    public ResponseEntity<String> createAndGiveResourceCard(@PathVariable Long playerId, @RequestBody NewResourceCardRequest request){
        return ResponseEntity.ok(service.createAndGiveResourceCard(playerId, request));
    }
    @PostMapping("/{playerId}/create-give-representation-card")
    public ResponseEntity<String> createAndGiveRepresentationCard(@PathVariable Long playerId, @RequestBody NewRepresentationCardRequest request) throws InstanceNotFoundException {
        return ResponseEntity.ok(service.createAndGiveRepresentationCard(playerId, request));
    }
    @PostMapping("/{playerId}/create-give-market-card")
    public ResponseEntity<String> createAndGiveMarketCard(@PathVariable Long playerId, @RequestBody NewMarketCardRequest request){
        return ResponseEntity.ok(service.createAndGiveMarketCard(playerId, request));
    }
    @PostMapping("/{playerId}/create-give-extra-card")
    public ResponseEntity<String> createAndGiveExtraCard(@PathVariable Long playerId, @RequestBody ExtraCardRequest request){
       return ResponseEntity.ok(service.createAndGiveExtraCard(playerId, request));
    }
    @PostMapping("/{playerId}/create-give-action-card")
    public ResponseEntity<String> createAndGiveActionCard(@PathVariable Long playerId, @RequestBody NewActionCardRequest request){
        return ResponseEntity.ok(service.createAndGiveActionCard(playerId, request));
    }
    @PostMapping("/{playerId}/create-give-battle-card")
    public ResponseEntity<String> createAndGiveBattleCard(@PathVariable Long playerId, @RequestBody NewBattleCardRequest request){
        return ResponseEntity.ok(service.createAndGiveBattleCard(playerId, request));
    }
    @PostMapping("/create-assign-personal-prices")
    public ResponseEntity<String> createAndAssignPersonalPrice(@RequestParam Long playerDataId){
        return ResponseEntity.ok((service.createAndAssignPersonalPrice(playerDataId)));
    }

    @PostMapping("/{idCard}/move-card")
    public ResponseEntity<String> moveCard(@RequestBody MoveCardRequest request,
                                           @PathVariable Long idCard){
        return ResponseEntity.ok(service.moveCard(request.getFromId(), request.getToId(), idCard));
    }

    @DeleteMapping("/{idCard}/remove-card")
    public ResponseEntity<String> removeCard(@PathVariable Long idCard){
        return ResponseEntity.ok(service.removeCard(idCard));
    }

    @GetMapping ("/game")
    public ResponseEntity<GameDataFullResponse> getFullData(){
        return ResponseEntity.ok(service.getFullData());
    }
    @GetMapping ("/control-data")
    public ResponseEntity<ControlResponse> getControlData(){
        return ResponseEntity.ok(service.getControlData());
    }
    @PostMapping("/{id}/edit-city")
    public ResponseEntity<String> editCity(@RequestBody Map<String, Integer> request, @PathVariable Long id){
        return ResponseEntity.ok(service.editCity(request, id));
    }
    @PostMapping("/{cityId}/assign-diputado")
    public ResponseEntity<String> assignNewDiputadoToCity(@PathVariable Long cityId, @RequestBody AssignDiputadoRequest diputado) throws InstanceNotFoundException {
        return ResponseEntity.ok(service.assignNewDiputadoToCity(cityId, diputado.getDiputadoId()));
    }
    @PostMapping("/{cityId}/remove-building")
    public ResponseEntity<String> removeBuilding(@PathVariable Long cityId, @RequestParam Long buildingId){
        return ResponseEntity.ok(service.removeBuilding(cityId, buildingId));
    }
    @PostMapping("/{cityId}/add-building")
    public ResponseEntity<String> addBuilding(@PathVariable Long cityId, @RequestBody NewBuildingRequest request){
        return ResponseEntity.ok(service.addBuilding(cityId, request));
    }
    @PostMapping("/{playerId}/update-plata")
    public ResponseEntity<String> addPlata(@PathVariable Long playerId, @RequestBody SoleValueRequest plata){
        return ResponseEntity.ok(service.addPlata(playerId, plata.getNewValue()));
    }
    @PostMapping("/{routeId}/update-route")
    public ResponseEntity<String> updateRoute(@PathVariable Long routeId, @RequestBody AssignRouteValueRequest request){
        return ResponseEntity.ok(service.updateRoute(routeId, request));
    }

    @PostMapping("/{priceId}/update-price")
    public ResponseEntity<String> updatePrices(@PathVariable Long priceId,
                                               @RequestBody Map<String, Integer> request){
        return ResponseEntity.ok(service.updatePrices(priceId, request));
    }

    @PostMapping("/{votationId}/update-votation")
    public ResponseEntity<String> updateVotation(@PathVariable Long votationId, @RequestBody UpdateVotationRequest request){
        return ResponseEntity.ok(service.updateVotation(votationId, request));
    }
    @PostMapping("/{votationId}/add-vote")
    public ResponseEntity<String> addVote(@PathVariable Long votationId, @RequestBody NewVoteRequest request){
        return ResponseEntity.ok(service.addVote(votationId, request));
    }
    @PostMapping("/{voteId}/update-vote")
    public ResponseEntity<String> updateVote(@PathVariable Long voteId, @RequestBody UpdateVoteRequest request){
        return ResponseEntity.ok(service.updateVote(voteId, request));
    }
    @PostMapping("/create-battle")
    public ResponseEntity<String> createBattle(@RequestBody CreateBattleRequest request){
        return ResponseEntity.ok(service.createBattle(request));
    }
    @PostMapping("/{battleId}/assign-random-values-battle")
    public ResponseEntity<String> assignRandomValuesBattle(@PathVariable Long battleId){
        return ResponseEntity.ok(service.assignRandomValuesBattle(battleId));
    }

    @PostMapping("/solve-battle")
    public ResponseEntity<String> solveBattle(@RequestBody SolveBattleRequest request){
        return ResponseEntity.ok(service.solveBattle(request));
    }
    @PostMapping("/{playerId}/assign-reserve")
    public ResponseEntity<String> assignReserve(@PathVariable Long playerId, @RequestBody SoleValueRequest militia){
        return ResponseEntity.ok(service.assignReserve(playerId, militia.getNewValue()));
    }
    @DeleteMapping("/{armyId}")
    public ResponseEntity<String> deleteArmy(@PathVariable Long armyId){
        return ResponseEntity.ok(service.deleteArmy(armyId));
    }
    @PostMapping("/new-army")
    public ResponseEntity<String> createArmy(@RequestBody NewArmyRequest request){
        return ResponseEntity.ok(service.createArmy(request));
    }
    @PostMapping("/move-camp")
    public ResponseEntity<String> moveCamp(@RequestBody MoveCampRequest request){
        return ResponseEntity.ok(service.moveCamp(request));
    }
    @PostMapping("/assign-congress-president")
    public ResponseEntity<String> assignCongressPresident(@RequestParam Long revolucionarioId){
        return ResponseEntity.ok(service.assignCongressPresident(revolucionarioId));
    }
    @DeleteMapping("/{congresoId}/remove-congress")
    public ResponseEntity<String> removeCongress(@PathVariable Long congresoId){
        return ResponseEntity.ok(service.removeCongress(congresoId));
    }
    @PatchMapping("/{congresoId}/update-congress")
    public ResponseEntity<String> updateCongress(@PathVariable Long congresoId, @RequestBody UpdateCongressRequest request){
        return ResponseEntity.ok(service.updateCongress(congresoId, request));
    }
    @PostMapping("/create-new-congress")
    public ResponseEntity<String> createNewCongress(@RequestBody CreateNewCongressRequest request){
        return ResponseEntity.ok(service.createNewCongress(request));
    }
    @PostMapping("/move-to-congress")
    public ResponseEntity<String> moveToCongress(@RequestBody MoveToCongressRequest request){
        return ResponseEntity.ok(service.moveToCongress(request));
    }

    @PostMapping("/conclude-phase")
    public ResponseEntity<Void> concludePhase(){
        service.concludePhase();
        return ResponseEntity.ok().build();
    }

}
