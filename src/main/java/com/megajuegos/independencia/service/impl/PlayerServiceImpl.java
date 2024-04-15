package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.exceptions.PlayerNotFoundException;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.PlayerService;
import com.megajuegos.independencia.service.util.GameIdUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.megajuegos.independencia.util.Messages.CARD_GIVEN;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerDataRepository repository;
    private final UserUtil userUtil;
    private final GameIdUtil gameIdUtil;

    @Override @Transactional
    public String giveCard(Long jugador, Long cardId) {
        PlayerData myPlayerData = repository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

        Card card = null;
        for(Card c : myPlayerData.getCards()){
            if(Objects.equals(c.getId(), cardId)){
                card = c;
                break;
            }
        }

        PlayerData they = repository.findById(jugador)
                .orElseThrow(() -> new PlayerNotFoundException());
        if(card!=null){
            they.getCards().add(card);
            myPlayerData.getCards().remove(card);
        }
        repository.save(myPlayerData);
        repository.save(they);

        return CARD_GIVEN;
    }
}
