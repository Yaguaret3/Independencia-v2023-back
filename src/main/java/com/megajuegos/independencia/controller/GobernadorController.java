package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.gobernador.*;
import com.megajuegos.independencia.dto.response.GobernadorResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyGobernadorResponse;
import com.megajuegos.independencia.dto.response.tiny.GameRegionTinyResponse;
import com.megajuegos.independencia.service.GobernadorService;
import com.megajuegos.independencia.service.util.PaymentRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/ciudad")
public class GobernadorController {

    private GobernadorService service;

    @Autowired
    public GobernadorController(@Valid GobernadorService service){
        this.service = service;
    }

    @GetMapping
    public GobernadorResponse getData(){
        return service.getData();
    }

    @GetMapping("/game/")
    public ResponseEntity<GameDataTinyGobernadorResponse> getGameData(){
        return ResponseEntity.ok(service.getGameData());
    }

    @PostMapping("/sell-marketplace")
    @Validated
    public void sellMarketPlace(@Valid @RequestBody MarketPlaceSellRequest request){
        service.sellMarketPlace(request);
    }

    @PostMapping("/give-representation-card")
    public void giveRepresentationCard(@Valid @RequestBody RepresentationRequest request){
        service.giveRepresentationRequest(request);
    }

    @PostMapping("/change-taxes")
    @Validated
    public void changeTaxes(@Valid @RequestBody TaxesRequest request){
        service.changeTaxes(request);
    }

    @PostMapping("/build-new-building")
    public void buildNewBuilding(@Valid @RequestBody UpgradeBuildingRequest request) throws InstanceNotFoundException {
        service.buildNewBuilding(request);
    }

    @PostMapping("/upgrade-marketplace")
    public void upgradeMarketplace(@Valid @RequestBody PaymentRequestUtil request){
        service.upgradeMarketplace(request);
    }

    @PostMapping("/promote-corruption")
    public void promoteCorruption(@Valid CorruptionRequest request){
        service.promoteCorruption(request);
    }

    @PostMapping("/recruit-militia")
    public void recruitMilitia(@Valid @RequestBody PaymentRequestUtil recursos){
        service.recruitMilitia(recursos);
    }

    @PostMapping("/assign-militia")
    public void assignMilitia(@Valid @RequestBody AssignMilitiaRequest request){
        service.assignMilitia(request);
    }

    @PostMapping("/finance-congress")
    public void financeCongress(@Valid FinanceCongressRequest request){
        service.financeCongress(request);
    }
}
