package com.megajuegos.independencia.service.util;

import com.megajuegos.independencia.entities.data.GameData;
import com.megajuegos.independencia.exceptions.GameDataNotFoundException;
import com.megajuegos.independencia.repository.GameDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameIdUtil {

    private final GameDataRepository gameDataRepository;

    public Long currentGameId(){
        return gameDataRepository.findFirstByOrderById()
                .map(GameData::getId)
                .orElseThrow(GameDataNotFoundException::new);
    }
}
