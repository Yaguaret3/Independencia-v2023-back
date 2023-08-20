package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.capitan.*;
import com.megajuegos.independencia.dto.response.CapitanResponse;
import com.megajuegos.independencia.service.CapitanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/militares")
@RequiredArgsConstructor
public class CapitanController {

    private final CapitanService service;

    @GetMapping
    public CapitanResponse getData(){
        return service.getData();
    }

    @PostMapping("/buy-action-cards")
    public void buyActionCards(@Valid BuyRequest request) throws InstanceNotFoundException {
        service.buyActionCards(request);
    }

    @PostMapping("/buy-battle-cards")
    public void buyBattleCards(@Valid BuyRequest request) throws InstanceNotFoundException {
        service.buyBattleCards(request);
    }

    @PostMapping ("/play-action-cards")
    public void playActionCards(@Valid ActionRequest request){
        service.playActionRequest(request);
    }

    @PostMapping("/play-battle-cards")
    public void playBattleCards(@Valid BattleRequest request){
        service.playBattleCards(request);
    }

    @PostMapping("/make-camp")
    public void makeCamp(@Valid CampRequest request){
        service.makeCamp(request);
    }

    @PostMapping("/upgrade-camp")
    public void upgradeCamp(@Valid BuyRequest request){
        service.upgradeCamp(request);
    }

    @PostMapping("/spend-discipline")
    public void spendDiscipline(@Valid DisciplineRequest request){
        service.spendDiscipline(request);
    }

}
