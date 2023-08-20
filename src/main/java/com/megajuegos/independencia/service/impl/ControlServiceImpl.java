package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.response.GameDataFullResponse;
import com.megajuegos.independencia.entities.Battle;
import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.MarketCard;
import com.megajuegos.independencia.entities.card.RepresentationCard;
import com.megajuegos.independencia.entities.card.ResourceCard;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.RepresentationEnum;
import com.megajuegos.independencia.enums.ResourceTypeEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.GobernadorDataRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.ControlService;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.megajuegos.independencia.util.Messages.*;

@Service
@RequiredArgsConstructor
public class ControlServiceImpl implements ControlService {

    private final GameDataRepository gameDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final CardRepository cardRepository;
    private final BattleRepository battleRepository;
    private final GobernadorDataRepository gobernadorRepository;
    private final UserUtil userUtil;
    private final PersonalPriceRepository priceRepository;
    private final CongresoRepository congresoRepository;

    @Override
    public String advanceTurn(Long gameDataId) {
        GameData gameData = gameDataRepository.findById(gameDataId)
                .orElseThrow(GameDataNotFoundException::new);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 15);
        Long newTurnInMillis = cal.getTimeInMillis();

        gameData.setTurno(gameData.getTurno()+1);
        gameData.setNextEndOfTurn(newTurnInMillis);

        //collectTaxes(gameData);
        //earnDiscipline(gameData);

        gameDataRepository.save(gameData);

        return NEW_TURN_STARTED;
    }

    @Override
    public String createAndGiveResourceCard(Long playerDataId) {

        PlayerData playerData = playerDataRepository.findById(playerDataId)
                .orElseThrow(PlayerNotFoundException::new);

        playerData.getCards().add(ResourceCard.builder()
                        .resourceTypeEnum(ResourceTypeEnum.METALMECANICA)
                        .build());
        playerDataRepository.save(playerData);
        return CARD_CREATED_GIVEN;
    }
    @Override
    public String createAndGiveRepresentationCard(Long playerDataId) throws InstanceNotFoundException {
        GobernadorData gobernadorData = gobernadorRepository.findById(playerDataId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        gobernadorData.getCards().add(RepresentationCard.builder()
                        .representacion(RepresentationEnum.byNombre(gobernadorData.getCity().getName()))
                        .build());

        playerDataRepository.save(gobernadorData);
        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndGiveMarketCard(Long playerDataId) {
        PlayerData playerData = playerDataRepository.findById(playerDataId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        if(playerData instanceof GobernadorData){

            GobernadorData gobernadorData = (GobernadorData) playerData;

            gobernadorData.getCards().addAll(
                    Arrays.asList(
                            MarketCard.builder()
                                    .nombreCiudad(gobernadorData.getCity().getName())
                                    .level(2)
                                    .build(),
                            MarketCard.builder()
                                    .nombreCiudad(gobernadorData.getCity().getName())
                                    .level(1)
                                    .build()
                    )
            );
            playerDataRepository.save(gobernadorData);
        }

        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndAssignPersonalPrice(Long playerDataId) {
        PlayerData playerData = playerDataRepository.findById(playerDataId)
                .orElseThrow(PlayerNotFoundException::new);

        PersonalPrice price = priceRepository.save(
                                        PersonalPrice.builder()
                                                .agropecuaria(0)
                                                .comercial(0)
                                                .construccion(0)
                                                .disciplina(0)
                                                .metalmecanica(0)
                                                .plata(0)
                                                .puntajeComercial(0)
                                                .textil(0)
                                                .build()
                                );
        playerData.getPrices().add(price);
        playerDataRepository.save(playerData);
        return PERSONAL_PRICE_ASSIGNED;
    }

    @Override
    public String assignCongressPresident(Long revolucionarioId) {
        RevolucionarioData revolucionario;
        Optional<PlayerData> optional = playerDataRepository.findById(revolucionarioId);
        if(!optional.isPresent() || !(optional.get() instanceof RevolucionarioData)){
            throw new PlayerNotFoundException();
        }
        revolucionario = (RevolucionarioData) optional.get();

        Congreso congreso = revolucionario.getCongreso();
        congreso.setPresidente(revolucionario);

        congresoRepository.save(congreso);

        return CONGRESS_PRESIDENT_ASSIGNED;
    }

    @Override
    public String moveCard(Long fromId, Long toId, Long cartaId) {
        PlayerData from = playerDataRepository.findById(fromId)
                .orElseThrow(PlayerNotFoundException::new);

        PlayerData to = playerDataRepository.findById(toId)
                .orElseThrow(PlayerNotFoundException::new);

        Card card = null;
        for(Card c : from.getCards()){
            if(Objects.equals(c.getId(), cartaId)){
                card = c;
                break;
            }
        }
        if(card!=null){
            to.getCards().add(card);
            from.getCards().remove(card);
        }
        playerDataRepository.save(from);
        playerDataRepository.save(to);

        return CARD_MOVED;
    }

    @Override
    public String removeCard(Long idCard) {
        Card card = cardRepository.findById(idCard).orElseThrow(CardNotFoundException::new);
        PlayerData playerData = card.getPlayerData();
        playerData.getCards().remove(card);
        playerDataRepository.save(playerData);
        return CARD_REMOVED;
    }

    @Override
    public GameDataFullResponse getFullData() {

        //return GameDataFullResponse.toFullResponse(getPlayerData().getGameData());
        return null;
    }

    @Override
    public String solveBattle(Long id) {

        Battle battle = battleRepository.findById(id).orElseThrow(BattleNotFoundException::new);
        return null;
    }

    private void collectTaxes(GameData gameData){

        Set<PlayerData> players = gameData.getPlayers();
        List<GobernadorData> gobernadores = players
                                            .stream()
                                            .filter(GobernadorData.class::isInstance)
                                            .map(GobernadorData.class::cast)
                                            .collect(Collectors.toList());

        for(GobernadorData gobernador : gobernadores){
            gobernador.setPlata(gobernador.getPlata() + gobernador.getCity().getTaxesLevel());
        }

    }
    private void earnDiscipline(GameData gameData){

        Set<PlayerData> players = gameData.getPlayers();
        List<CapitanData> capitanes = players
                                            .stream()
                                            .filter(CapitanData.class::isInstance)
                                            .map(CapitanData.class::cast)
                                            .collect(Collectors.toList());

        for(CapitanData capitan : capitanes){
            capitan.setDisciplina(capitan.getDisciplina() + capitan.getCamp().getNivel());
        }
    }
}
