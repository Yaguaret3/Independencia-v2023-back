package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.entities.card.BattleCard;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.ResourceCard;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.exceptions.CardCannotBeGivenException;
import com.megajuegos.independencia.exceptions.CardNotFoundException;
import com.megajuegos.independencia.exceptions.PlayerNotFoundException;
import com.megajuegos.independencia.repository.CardRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.PlayerService;
import com.megajuegos.independencia.service.util.GameIdUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerDataRepository repository;
    private final CardRepository cardRepository;
    private final UserUtil userUtil;
    private final GameIdUtil gameIdUtil;

    @Override
    public void giveCard(Long jugador, Long cardId) {
        PlayerData myPlayerData = repository.findById(userUtil.getCurrentUser().getPlayerDataList().stream()
                        .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                        .findFirst()
                        .map(PlayerData::getId)
                        .orElseThrow(() -> new PlayerNotFoundException())
                )
                .orElseThrow(() -> new PlayerNotFoundException());

        Card card = myPlayerData.getCards().stream()
                .filter(c -> Objects.equals(c.getId(), cardId))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException());

        PlayerData they = repository.findById(jugador)
                .orElseThrow(() -> new PlayerNotFoundException());

        validarDarCarta(myPlayerData, card, they);

        they.getCards().add(card);
        myPlayerData.getCards().remove(card);
        card.setPlayerData(they);

        repository.saveAll(Arrays.asList(myPlayerData, they));
        cardRepository.save(card);
    }

    ////////////////////////////////////////////////////////////////////
    //////////////////////// MÉTODOS PRIVADOS //////////////////////////
    ////////////////////////////////////////////////////////////////////

    private void validarDarCarta(PlayerData player, Card card, PlayerData playerTo){

        if(player instanceof GobernadorData){

            if(!(card instanceof ResourceCard && (playerTo instanceof RevolucionarioData || playerTo instanceof CapitanData))){
                throw new CardCannotBeGivenException();
            }
        }
        if(player instanceof MercaderData){
            if(!(card instanceof ResourceCard && playerTo instanceof GobernadorData)){
                throw new CardCannotBeGivenException();
            }
        }
        if(player instanceof RevolucionarioData){
            if(!(card instanceof ResourceCard)){
                throw new CardCannotBeGivenException();
            }
        }
        if(player instanceof CapitanData){
            if(!(card instanceof BattleCard && playerTo instanceof CapitanData)){
                throw new CardCannotBeGivenException();
            }
        }
    }
}