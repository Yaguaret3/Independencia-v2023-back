package com.megajuegos.independencia.controller;

import com.megajuegos.independencia.dto.request.player.GiveCardRequest;
import com.megajuegos.independencia.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService service;

    @PostMapping("/give-card")
    public ResponseEntity<Void> giveCard(@RequestBody GiveCardRequest request) {
        service.giveCard(request.getPlayerToId(), request.getCardId());
        return ResponseEntity.ok().build();
    }
}
