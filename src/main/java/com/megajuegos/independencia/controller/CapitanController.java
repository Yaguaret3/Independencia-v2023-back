package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.capitan.*;
import com.megajuegos.independencia.dto.response.CapitanResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyCapitanResponse;
import com.megajuegos.independencia.service.CapitanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/militares")
@RequiredArgsConstructor
public class CapitanController {

    private final CapitanService service;

    @GetMapping
    public ResponseEntity<CapitanResponse> getData(){
        return ResponseEntity.ok(service.getData());
    }

    @GetMapping("/get-game-data")
    public ResponseEntity<GameDataTinyCapitanResponse> getGameData(){
        return ResponseEntity.ok(service.getGameData());
    }

    @PostMapping("/buy-action-cards")
    public void buyActionCards(@Valid BuyRequest request) throws InstanceNotFoundException {
        service.buyActionCards(request);
    }

    @PostMapping("/buy-battle-cards")
    public void buyBattleCards(@Valid BuyRequest request) throws InstanceNotFoundException {
        service.buyBattleCards(request);
    }

    @PostMapping("/move")
    public void move(@RequestBody MovementRequest request){
        service.move(request);
    }

    @PostMapping ("/play-action-cards")
    public void playActionCards(@RequestBody @Valid ActionRequest request){
        service.playActionRequest(request);
    }

    @PostMapping("/play-battle-cards")
    public void playBattleCards(@RequestBody @Valid BattleRequest request){
        service.playBattleCards(request);
    }

    @PostMapping("/make-camp")
    public void makeCamp(@RequestBody @Valid CampRequest request){
        service.makeCamp(request);
    }

    @PostMapping("/rush")
    public void rush(@RequestBody @Valid ActionRequest request){
        service.rush(request);
    }

}
