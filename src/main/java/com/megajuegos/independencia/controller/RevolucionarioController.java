package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.revolucionario.VoteProposalRequest;
import com.megajuegos.independencia.dto.request.revolucionario.VoteRequest;
import com.megajuegos.independencia.dto.response.CongresoResponse;
import com.megajuegos.independencia.dto.response.RevolucionarioResponse;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.service.RevolucionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CongresoResponse>> getCongresosData(){
        return ResponseEntity.ok(service.getCongresosData());
    }

    @PostMapping("/propose")
    public ResponseEntity<Void> propose(@RequestBody @Valid VoteProposalRequest request) {
        service.propose(request.getProposal());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@Valid @RequestBody VoteRequest request){
        service.vote(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/close-votation")
    public ResponseEntity<Void> closeVotation(){
        service.closeVotation();
        return ResponseEntity.ok().build();
    }
}
