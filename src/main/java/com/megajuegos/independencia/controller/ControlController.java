package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.capitan.BattleRequest;
import com.megajuegos.independencia.dto.request.control.*;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import com.megajuegos.independencia.service.ControlService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/control")
@RequiredArgsConstructor
public class ControlController {

    private final ControlService service;

    @PostMapping("/create-give-resource-card")
    public ResponseEntity<String> createAndGiveCard(@RequestParam Long playerDataId){
        return ResponseEntity.ok(service.createAndGiveResourceCard(playerDataId));
    }
    @PostMapping("/create-give-representation-card")
    public ResponseEntity<String> createAndGiveRepresentationCard(@RequestParam Long playerDataId) throws InstanceNotFoundException {
        return ResponseEntity.ok(service.createAndGiveRepresentationCard(playerDataId));
    }
    @PostMapping("/create-give-market-card")
    public ResponseEntity<String> createAndGiveMarketCard(@RequestBody NewMarketCardRequest request){
        return ResponseEntity.ok(service.createAndGiveMarketCard(request));
    }
    @PostMapping("/create-give-extra-card")
    public ResponseEntity<String> createAndGiveExtraCard(@RequestBody ExtraCardRequest request, @RequestParam Long playerDataId){
       return ResponseEntity.ok(service.createAndGiveExtraCard(request, playerDataId));
    }
    @PostMapping("/create-assign-personal-prices")
    public ResponseEntity<String> createAndAssignPersonalPrice(@RequestParam Long playerDataId){
        return ResponseEntity.ok((service.createAndAssignPersonalPrice(playerDataId)));
    }

    @PostMapping("/move-card")
    public ResponseEntity<String> moveCard(@RequestParam(name = "from") Long from,
                                           @RequestParam(name = "to") Long to,
                                           @RequestParam(name = "carta") Long carta){
        return ResponseEntity.ok(service.moveCard(from, to, carta));
    }

    @PostMapping("/remove-card")
    public ResponseEntity<String> removeCard(@RequestParam Long idCard){
        return ResponseEntity.ok(service.removeCard(idCard));
    }

    @GetMapping ("/game")
    public ResponseEntity<GameDataFullResponse> getFullData(){
        return ResponseEntity.ok(service.getFullData());
    }
    @PatchMapping("/{id}/edit-city")
    public ResponseEntity<String> editCity(@RequestBody Map<String, String> request, @PathVariable Long id){
        return ResponseEntity.ok(service.editCity(request, id));
    }
    @PostMapping("/{cityId}/remove-building")
    public ResponseEntity<String> removeBuilding(@PathVariable Long cityId, @RequestParam Long buildingId){
        return ResponseEntity.ok(service.removeBuilding(cityId, buildingId));
    }
    @PostMapping("/{cityId}/add-building")
    public ResponseEntity<String> addBuilding(@PathVariable Long cityId, @RequestBody NewBuildingRequest request){
        return ResponseEntity.ok(service.addBuilding(cityId, request));
    }
    @PatchMapping("/{routeId}/assignFinalValue")
    public ResponseEntity<String> assignFinalRouteValue(@PathVariable Long routeId, @RequestBody AssignRouteValueRequest request){
        return ResponseEntity.ok(service.assignFinalRouteValue(routeId, request));
    }

    @PatchMapping("/{priceId}/update-price")
    public ResponseEntity<String> updatePrices(@PathVariable Long priceId,
                                               @RequestBody Map<String, Integer> request){
        return ResponseEntity.ok(service.updatePrices(priceId, request));
    }

    @PatchMapping("/{votationId}/update-votation")
    public ResponseEntity<String> updateVotation(@PathVariable Long votationId, @RequestBody UpdateVotationRequest request){
        return ResponseEntity.ok(service.updateVotation(votationId, request));
    }
    @PostMapping("/{votationId}/add-vote")
    public ResponseEntity<String> addVote(@PathVariable Long votationId, @RequestBody NewVoteRequest request){
        return ResponseEntity.ok(service.addVote(votationId, request));
    }
    @PatchMapping("/{voteId}/update-vote")
    public ResponseEntity<String> updateVote(@PathVariable Long voteId, @RequestBody UpdateVoteRequest request){
        return ResponseEntity.ok(service.updateVote(voteId, request));
    }
    @PostMapping("/create-battle")
    public ResponseEntity<String> createBattle(@RequestBody CreateBattleRequest request){
        return ResponseEntity.ok(service.createBattle(request));
    }
    @PostMapping("/{battleId}/asign-random-values-battle")
    public ResponseEntity<String> asignRandomValuesBattle(@PathVariable Long battleId){
        return ResponseEntity.ok(service.asignRandomValuesBattle(battleId));
    }

    @PostMapping("/solve-battle")
    public ResponseEntity<String> solveBattle(@RequestBody SolveBattleRequest request){
        return ResponseEntity.ok(service.solveBattle(request));
    }
    @PatchMapping("/{armyId}/assign-militia")
    public ResponseEntity<String> assignMilitia(@PathVariable Long armyId, @RequestBody Integer militia){
        return ResponseEntity.ok(service.assignMilitia(armyId, militia));
    }
    @PatchMapping("/{capitanId}/assign-reserve")
    public ResponseEntity<String> assignReserve(@PathVariable Long capitanId, @RequestBody Integer militia){
        return ResponseEntity.ok(service.assignReserve(capitanId, militia));
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

    @PostMapping("/phase-concluded")
    public ResponseEntity<Void> concludePhase(){
        service.concludePhase();
        return ResponseEntity.ok().build();
    }

}
