package com.megajuegos.independencia.service;

import com.megajuegos.independencia.dto.response.tiny.GameDataTinyResponse;

public interface PlayerService {

    String giveCard(Long jugador, Long card);
}
