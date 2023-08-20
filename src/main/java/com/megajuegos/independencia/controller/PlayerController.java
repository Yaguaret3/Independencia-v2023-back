package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.player.GiveCardRequest;
import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;
import com.megajuegos.independencia.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;

    @PostMapping("/give-card")
    public ResponseEntity<String> giveCard(@RequestBody GiveCardRequest request) {
        return ResponseEntity.ok(service.giveCard(request.getPlayerToId(), request.getCardId()));
    }
}
