package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.revolucionario.CloseVotationRequest;
import com.megajuegos.independencia.dto.request.revolucionario.VoteProposalRequest;
import com.megajuegos.independencia.dto.request.revolucionario.VoteRequest;
import com.megajuegos.independencia.dto.response.CongresoResponse;
import com.megajuegos.independencia.dto.response.RevolucionarioResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.service.RevolucionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/revolucion")
public class RevolucionarioController {

    private RevolucionarioService service;

    @Autowired
    public RevolucionarioController(RevolucionarioService service){
        this.service = service;
    }

    @GetMapping("/")
    public RevolucionarioResponse getData(){
        return service.getData();
    }

    @GetMapping("/game/")
    public ResponseEntity<GameDataTinyResponse> getGameData(){
        return ResponseEntity.ok(service.getGameData());
    }

    @GetMapping("/congresos/")
    public List<CongresoResponse> getCongresosData(){
        return service.getCongresosData();
    }

    @PostMapping("/propose")
    public String propose(@RequestBody @Valid VoteProposalRequest request) {
        return service.propose(request.getProposal());
    }

    @PostMapping("/vote")
    public String vote(@Valid @RequestBody VoteRequest request){
        return service.vote(request);
    }
    @PostMapping("/close-votation")
    public String closeVotation(){
        return service.closeVotation();
    }
}
