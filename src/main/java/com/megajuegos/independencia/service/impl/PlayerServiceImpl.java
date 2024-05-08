package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.entities.Log;
import com.megajuegos.independencia.entities.card.BattleCard;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.ResourceCard;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.LogTypeEnum;
import com.megajuegos.independencia.exceptions.CardCannotBeGivenException;
import com.megajuegos.independencia.exceptions.CardNotFoundException;
import com.megajuegos.independencia.exceptions.PlayerNotFoundException;
import com.megajuegos.independencia.repository.CardRepository;
import com.megajuegos.independencia.repository.LogRepository;
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
    private final LogRepository logRepository;

    @Override
    public void giveCard(Long jugador, Long cardId) {
        Long playerId = userUtil.getCurrentUser().getPlayerDataList().stream()
                .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                .findFirst()
                .map(PlayerData::getId)
                .orElseThrow(PlayerNotFoundException::new);
        PlayerData myPlayerData = repository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        Card card = myPlayerData.getCards().stream()
                .filter(c -> Objects.equals(c.getId(), cardId))
                .findFirst()
                .orElseThrow(() -> new CardNotFoundException(cardId));

        PlayerData they = repository.findById(jugador)
                .orElseThrow(() -> new PlayerNotFoundException(jugador));

        validarDarCarta(myPlayerData, card, they);

        they.getCards().add(card);
        myPlayerData.getCards().remove(card);
        card.setPlayerData(they);

        repository.saveAll(Arrays.asList(myPlayerData, they));
        cardRepository.save(card);

       String cardString = null;
        if(card instanceof ResourceCard){
            cardString = ((ResourceCard) card).getResourceTypeEnum().name();
        }
        if(card instanceof BattleCard){
            cardString = ((BattleCard) card).getTipoOrdenDeBatalla().getNombre();
        }

        Log log1 = Log.builder()
                .turno(myPlayerData.getGameData().getTurno())
                .tipo(LogTypeEnum.ENVIADO)
                .nota(String.format("Enviaste una carta de %s a %s", cardString, they.getUser().getUsername()))
                .player(myPlayerData)
                .build();

        Log log2 = Log.builder()
                .turno(myPlayerData.getGameData().getTurno())
                .tipo(LogTypeEnum.RECIBIDO)
                .nota(String.format("Has recibido una carta de %s de %s", cardString, myPlayerData.getUser().getUsername()))
                .player(they)
                .build();

        logRepository.saveAll(Arrays.asList(log1, log2));
    }

    ////////////////////////////////////////////////////////////////////
    //////////////////////// MÉTODOS PRIVADOS //////////////////////////
    ////////////////////////////////////////////////////////////////////

    private void validarDarCarta(PlayerData player, Card card, PlayerData playerTo){

        if(player instanceof GobernadorData){

            if(!(card instanceof ResourceCard && (playerTo instanceof RevolucionarioData || playerTo instanceof CapitanData))){
                throw new CardCannotBeGivenException(card.getId(), playerTo.getRol().name(), playerTo.getUser().getUsername());
            }
        }
        if(player instanceof MercaderData){
            if(!(card instanceof ResourceCard && playerTo instanceof GobernadorData)){
                throw new CardCannotBeGivenException(card.getId(), playerTo.getRol().name(), playerTo.getUser().getUsername());
            }
        }
        if(player instanceof RevolucionarioData){
            if(!(card instanceof ResourceCard)){
                throw new CardCannotBeGivenException(card.getId(), playerTo.getRol().name(), playerTo.getUser().getUsername());
            }
        }
        if(player instanceof CapitanData){
            if(!(card instanceof BattleCard && playerTo instanceof CapitanData)){
                throw new CardCannotBeGivenException(card.getId(), playerTo.getRol().name(), playerTo.getUser().getUsername());
            }
        }
    }
}