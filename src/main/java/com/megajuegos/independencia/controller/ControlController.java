package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.control.NewMarketCardRequest;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;
import com.megajuegos.independencia.service.ControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;

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
    @PostMapping("/create-assign-personal-prices")
    public ResponseEntity<String> createAndAssignPersonalPrice(@RequestParam Long playerDataId){
        return ResponseEntity.ok((service.createAndAssignPersonalPrice(playerDataId)));
    }

    @PostMapping("/advance-turn")
    public ResponseEntity<String> advanceTurn(@RequestParam Long gameDataId){
        return ResponseEntity.ok(service.advanceTurn(gameDataId));
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

    @PostMapping("/solve-battle")
    public ResponseEntity<String> solveBattle(@RequestParam Long battle){
        return ResponseEntity.ok(service.solveBattle(battle));
    }

    @PostMapping("/assign-congress-president")
    public ResponseEntity<String> assignCongressPresident(@RequestParam Long revolucionarioId){
        return ResponseEntity.ok(service.assignCongressPresident(revolucionarioId));
    }

}
