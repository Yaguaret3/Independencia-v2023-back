package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.control.*;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
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
    public ResponseEntity<String> removeCard(@RequestParam (name = "id") Long idCard){
        return ResponseEntity.ok(service.removeCard(idCard));
    }

    @GetMapping ("/get-full-data")
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

    @PostMapping("/solve-battle")
    public ResponseEntity<String> solveBattle(@RequestParam Long battle){
        return ResponseEntity.ok(service.solveBattle(battle));
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
