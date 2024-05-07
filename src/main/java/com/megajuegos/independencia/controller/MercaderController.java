package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.mercader.*;
import com.megajuegos.independencia.dto.response.MercaderResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.service.MercaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/comercio")
public class MercaderController {

    private MercaderService service;

    @Autowired
    public MercaderController(MercaderService service){
        this.service = service;
    }

    @GetMapping
    public MercaderResponse getData(){
        return service.getData();
    }

    @PostMapping("/play-trade-routes")
    public void playTradeRoutes(@Valid @RequestBody SingleTradeRouteRequest request) {
        service.playTradeRoutes(request);
    }

    @PostMapping("/buy-resources")
    public void buyResources(@Valid @RequestBody ResourceRequest request) {
        service.buyResources(request);
    }

    @PostMapping("/upgrade-prices")
    public void upgradePrices(@Valid PricesRequest request) throws InstanceNotFoundException {
        service.upgradePrices(request);
    }

    @GetMapping("/get-game-data")
    public ResponseEntity<GameDataTinyResponse> getGameData(){
        return ResponseEntity.ok(service.getGameData());
    }
}
