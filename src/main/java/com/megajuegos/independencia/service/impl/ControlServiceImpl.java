package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.control.*;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;
import com.megajuegos.independencia.entities.*;
import com.megajuegos.independencia.entities.card.*;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.PhaseEnum;
import com.megajuegos.independencia.enums.RepresentationEnum;
import com.megajuegos.independencia.enums.ResourceTypeEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.CapitanDataRepository;
import com.megajuegos.independencia.repository.data.ControlDataRepository;
import com.megajuegos.independencia.repository.data.GobernadorDataRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.ControlService;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.management.InstanceNotFoundException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.megajuegos.independencia.util.Messages.*;

@Service
@RequiredArgsConstructor
public class ControlServiceImpl implements ControlService {

    private final GameDataRepository gameDataRepository;
    private final ControlDataRepository controlDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final CardRepository cardRepository;
    private final BattleRepository battleRepository;
    private final GobernadorDataRepository gobernadorRepository;
    private final CapitanDataRepository capitanRepository;
    private final UserUtil userUtil;
    private final PersonalPriceRepository priceRepository;
    private final CongresoRepository congresoRepository;
    private final CityRepository cityRepository;
    private final RouteRepository routeRepository;
    private final VoteRepository voteRepository;
    private final VotationRepository votationRepository;
    private final ArmyRepository armyRepository;
    private final GameSubRegionRepository gameSubRegionRepository;

    private static final int DADO_CUATRO_MAX = 4;
    private static final int DADO_CUATRO_MIN = 1;
    private static final int CERO_DEFAULT = 0;
    private static final int UN_SOLO_ATACANTE = 1;

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
    public String createAndGiveMarketCard(NewMarketCardRequest request) {
        PlayerData playerData = playerDataRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        playerData.getCards().add(MarketCard.builder()
                        .nombreCiudad(cityRepository.findByName(request.getCityName()).orElseThrow(() -> new CityNotFoundException()).getName())
                        .level(request.getLevel())
                .build());

        playerDataRepository.save(playerData);

        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndGiveExtraCard(ExtraCardRequest request, Long playerDataId) {

        PlayerData playerData = playerDataRepository.findById(playerDataId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        playerData.getCards().add(ExtraCard.builder()
                    .bonificacion(request.getBonificacion())
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                .build());

        playerDataRepository.save(playerData);
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
    public void concludePhase() {
        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        GameData gameData = controlData.getGameData();

        if(controlData.getSiguienteFaseSolicitada()){
           controlData.setSiguienteFaseSolicitada(false);
           controlDataRepository.save(controlData);
           return;
        }

        controlData.setSiguienteFaseSolicitada(true);

        //Chequear si al actualizar controlData cambia también su referencia desde la lista de Controles.
        List<ControlData> controles = gameData.getPlayers().stream()
                .filter(ControlData.class::isInstance)
                .map(p -> (ControlData) p)
                .collect(Collectors.toList());

        List<ControlData> controlesQueFaltanTerminar = controles.stream()
                .filter(c -> !c.getSiguienteFaseSolicitada())
                .collect(Collectors.toList());

        if(controlesQueFaltanTerminar.isEmpty()){
            switch (gameData.getFase()){
                case MOVING:
                    gameData.setFase(PhaseEnum.PLANNING);
                    break;
                case PLANNING:
                    gameData.setFase(PhaseEnum.PLANNING);
                    break;
                case REVEALING:
                    advanceTurn(gameData);
                    gameData.setFase(PhaseEnum.MOVING);
                    break;
            }
            gameDataRepository.save(gameData);
            controles.forEach(c -> c.setSiguienteFaseSolicitada(false));
            controlDataRepository.saveAll(controles);
            return;
        }

        controlDataRepository.save(controlData);
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

        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        return GameDataFullResponse.toFullResponse(controlData.getGameData());
    }

    @Override
    public String editCity(Map<String, Integer> request, Long id) {

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException());
        request.forEach((key, value) -> {

            Field field = ReflectionUtils.findField(City.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, city, value);
        });
        cityRepository.save(city);
        return CITY_UPDATED;
    }

    @Override
    public String removeBuilding(Long cityId, Long buildingId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException());

        Building building = city.getBuildings().stream().filter(b -> b.getId().equals(buildingId)).findFirst().orElseThrow(() -> new BuildingNotFoundException());

        city.getBuildings().remove(building);
        cityRepository.save(city);

        return BUILDING_REMOVED;
    }

    @Override
    public String addBuilding(Long cityId, NewBuildingRequest request) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException());

        city.getBuildings().add(Building.builder()
                        .buildingType(request.getBuildingType())
                        .city(city)
                .build());
        cityRepository.save(city);

        return BUILDING_CREATED;
    }

    @Override
    public String assignFinalRouteValue(Long routeId, AssignRouteValueRequest request) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new RouteNotFoundException());
        route.setTradeScore(request.getFinalValue());
        route.setComentario(request.getComentario());
        routeRepository.save(route);

        return TRADESCORE_ASSIGNED;
    }

    @Override
    public String updatePrices(Long priceId, Map<String, Integer> request) {
        PersonalPrice price = priceRepository.findById(priceId).orElseThrow(() -> new PriceNotFoundException());
        request.forEach((key, value) -> {

            Field field = ReflectionUtils.findField(PersonalPrice.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, price, value);
        });
        priceRepository.save(price);
        return PRICE_UPDATED;
    }

    @Override
    public String updateVotation(Long votationId, UpdateVotationRequest request) {
        Votation votation = votationRepository.findById(votationId).orElseThrow(() -> new VotationNotFoundException());
        if(request.getPropuesta() != null){
            votation.setPropuesta(request.getPropuesta());
        }
        if(request.getActive() != null){
            votation.setActive(request.getActive());
        }
        return VOTATION_UPDATED;
    }

    @Override
    public String addVote(Long votationId, NewVoteRequest request) {
        Votation votation = votationRepository.findById(votationId).orElseThrow(() -> new VotationNotFoundException());
        Vote vote = Vote.builder()
                .voteType(request.getVoteTypeEnum())
                .representacion(
                        Arrays.asList(
                                RepresentationCard.builder()
                                        .representacion(request.getRepresentationEnum())
                                        .build()
                        )
                )
                .build();
        voteRepository.save(vote);
        votation.getVotes().add(vote);
        votationRepository.save(votation);
        return VOTE_ADDED;
    }

    @Override
    public String updateVote(Long voteId, UpdateVoteRequest request) {
        Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new VoteNotFoundException());
        vote.setVoteType(request.getVoteType());
        voteRepository.save(vote);
        return VOTE_UPDATED;
    }

    @Override
    public String createBattle(CreateBattleRequest request) {

        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());
        GameData gameData = controlData.getGameData();

        GameSubRegion gameSubRegionInvolucrada = gameSubRegionRepository.findById(request.getGameSubRegionId())
                .orElseThrow(() -> new GameAreaNotFoundException());

        List<Army> ejercitosInvolucrados = armyRepository.findAllById(request.getCombatientes().stream()
                .map(ArmyForBattleRequest::getId)
                .collect(Collectors.toList()));

        if(!gameSubRegionInvolucrada.getEjercitos().containsAll(ejercitosInvolucrados)){
            throw new IncorrectBattleException();
        }

        ejercitosInvolucrados.forEach(e -> {
            Optional<ArmyForBattleRequest> requestedArmyId = request.getCombatientes().stream()
                    .filter(a -> a.getId().equals(e.getId()))
                    .findFirst();
            e.setAtaque(requestedArmyId.get().isAtaque());
            e.setMilicias(CERO_DEFAULT);
        });

        battleRepository.save(Battle.builder()
                        .active(true)
                        .turnoDeJuego(gameData.getTurno())
                        .gameSubRegion(gameSubRegionInvolucrada)
                        .combatientes(ejercitosInvolucrados)
                .build());
        return BATTLE_CREATED;
    }

    @Override
    public String asignRandomValuesBattle(Long battleId) {

        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        //Busca batallas y toma ejércitos involucrados
        Battle battle = battleRepository.findById(battleId).orElseThrow(() -> new BattleNotFoundException());
        List<Army> ejercitosInvolucrados = battle.getCombatientes();

        Random random = new Random();
        List<Integer> valoresAzar = new ArrayList<>();

        //Asigna iniciativa random y tira los dados
        ejercitosInvolucrados.forEach(e -> {
            e.setIniciativa(random.nextInt());
            valoresAzar.add(random.nextInt(DADO_CUATRO_MAX - DADO_CUATRO_MIN) + DADO_CUATRO_MIN);
        });

        //Ordena para hacer coincidir los ejércitos que atacan con los dados mayores
        ejercitosInvolucrados.sort(Comparator.comparing(Army::isAtaque).thenComparing(Army::getIniciativa));
        valoresAzar.sort(Comparator.reverseOrder());

        //Asigna valores
        int index = 0;
        while(index<ejercitosInvolucrados.size()){
            Army ejercito = ejercitosInvolucrados.get(index);
            ejercito.setValorAzar(valoresAzar.get(index));
            ejercito.setValorProvisorio(ejercito.getValorAzar() + ejercito.getMilicias());
            index++;
        }

        battleRepository.save(battle);
        return BATTLE_CREATED;
    }

    @Override
    public String assignMilitia(Long armyId, Integer militia) {

        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        Army army = armyRepository.findById(armyId).orElseThrow(() -> new ArmyNotFoundException());
        army.setMilicias(militia);
        armyRepository.save(army);
        return MILITIA_ASSIGNED;
    }

    @Override
    public String assignReserve(Long playerId, Integer militia) {
        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        PlayerData playerData = playerDataRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException());
        if(playerData instanceof CapitanData){
            CapitanData capitanData = (CapitanData) playerData;
            capitanData.setReserva(militia);
            playerDataRepository.save(capitanData);
        }
        if(playerData instanceof GobernadorData){
            GobernadorData gobernadorData = (GobernadorData) playerData;
            gobernadorData.setMilicia(militia);
            playerDataRepository.save(gobernadorData);
        }

        return RESERVE_ASSIGNED;
    }

    @Override
    public String deleteArmy(Long armyId) {
        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        Army army = armyRepository.findById(armyId).orElseThrow(() -> new ArmyNotFoundException());

        CapitanData capitanData = army.getCapitanData();
        capitanData.getEjercito().remove(army);

        capitanRepository.save(capitanData);
        armyRepository.deleteById(armyId);

        return ARMY_DELETED;
    }

    @Override
    public String createArmy(NewArmyRequest request) {
        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        CapitanData capitanData = capitanRepository.findById(request.getCapitanId()).orElseThrow(() -> new PlayerNotFoundException());

        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getGameSubRegionId()).orElseThrow(() -> new GameAreaNotFoundException());

        Army army = Army.builder()
                .gameSubRegion(gameSubRegion)
                .capitanData(capitanData)
                .milicias(request.getMilicias())
                .build();

        armyRepository.save(army);
        capitanData.getEjercito().add(army);
        capitanRepository.save(capitanData);
        return ARMY_CREATED;
    }

    @Override
    public String moveCamp(MoveCampRequest request) {

        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        CapitanData capitanData = capitanRepository.findById(request.getCapitanId()).orElseThrow(() -> new PlayerNotFoundException());
        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getGameSubregionId()).orElseThrow(() -> new GameAreaNotFoundException());

        Camp camp = capitanData.getCamp();
        camp.setGameSubRegion(gameSubRegion);

        capitanRepository.save(capitanData);
        return CAMP_MOVED;
    }

    @Override
    public String assignNewDiputadoToCity(Long cityId, Long diputadoId) throws InstanceNotFoundException {
        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException());
        RevolucionarioData revolucionarioData = (RevolucionarioData) playerDataRepository.findById(diputadoId)
                                    .orElseThrow(PlayerNotFoundException::new);

        List<RepresentationCard> cards = cardRepository
                .findRepresentationCardByCity(RepresentationEnum.byNombre(city.getName()))
                .stream()
                .filter(r -> !r.isAlreadyPlayed())
                .collect(Collectors.toList());

        if(cards.isEmpty()){
            throw new CardNotFoundException();
        }
        if(cards.size()>1){
            throw new MoreThanOneRepresentationCardPerCity();
        }

        RepresentationCard representationCard = cards.get(0);
        PlayerData actualPlayer = representationCard.getPlayerData();
        actualPlayer.getCards().remove(representationCard);
        revolucionarioData.getCards().add(representationCard);

        city.setDiputado(revolucionarioData.getUsername());

        cityRepository.save(city);
        playerDataRepository.save(actualPlayer);
        playerDataRepository.save(revolucionarioData);

        return NEW_DIPUTADO_ASSIGNED;
    }

    @Override
    public String addPlata(Long playerId, Integer plata) {
        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        PlayerData playerData = playerDataRepository.findById(playerId)
                .orElseThrow(PlayerNotFoundException::new);

        if(playerData instanceof GobernadorData){
            GobernadorData gobernadorData = (GobernadorData) playerData;
            gobernadorData.setPlata(plata);
            playerDataRepository.save(gobernadorData);
        }
        if(playerData instanceof RevolucionarioData){
            RevolucionarioData revolucionarioData = (RevolucionarioData) playerData;
            revolucionarioData.setPlata(plata);
            playerDataRepository.save(revolucionarioData);
        }
        return PLATA_ASSIGNED;
    }

    @Override
    public String solveBattle(SolveBattleRequest request) {

        ControlData controlData = controlDataRepository.findById(userUtil.getCurrentUser().getPlayerDataId())
                .orElseThrow(() -> new PlayerNotFoundException());

        Battle battle = battleRepository.findById(request.getBattleId()).orElseThrow(BattleNotFoundException::new);
        GameSubRegion gameSubRegion = battle.getGameSubRegion();

        List<Army> ejercitos = battle.getCombatientes();
        ejercitos.forEach(army -> {

            request.getResultados().forEach(r -> {

                if(Objects.equals(army.getId(), r.getArmyId()) && r.isDestruido()){

                    //Si el ejército fue derrotado...
                    CapitanData capitanData = army.getCapitanData();
                    //Resta milicias a la reserva del capitán.
                    capitanData.setReserva(capitanData.getReserva() - r.getMiliciasPerdidas());
                    //Quita el ejército de la subregión (y del capitán)
                    capitanData.getEjercito().remove(army);
                    gameSubRegion.getEjercitos().remove(army);

                }
                if(Objects.equals(army.getId(), r.getArmyId()) && !r.isDestruido()){
                    //Si el ejército no fue derrotado, se limpia para otra batalla.
                    army.setAtaque(false);
                    army.setIniciativa(0);
                    army.setMilicias(0);
                    army.setValorAzar(0);
                    army.setValorProvisorio(0);
                    army.setCartasJugadas(new ArrayList<>());
                }
            });

            //Se descartan las cartas jugadas
            army.getCartasJugadas().forEach(c -> {
                c.setAlreadyPlayed(true);
                c.setTurnWhenPlayed(controlData.getGameData().getTurno());
            });
        });

        //Desactiva la batalla
        battle.setActive(false);

        gameSubRegionRepository.save(gameSubRegion);
        cardRepository.saveAll(battle.getCombatientes().stream()
                .flatMap(c -> c.getCartasJugadas().stream())
                .collect(Collectors.toList()));
        battleRepository.save(battle);
        playerDataRepository.saveAll(battle.getCombatientes().stream()
                .map(Army::getCapitanData)
                .collect(Collectors.toList()));

        return BATTLE_SOLVED;
    }

    private void advanceTurn(GameData gameData) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 15);
        Long newTurnInMillis = cal.getTimeInMillis();

        gameData.setTurno(gameData.getTurno()+1);
        gameData.setNextEndOfTurn(newTurnInMillis);

        //collectTaxes(gameData);
        //earnDiscipline(gameData);

        gameDataRepository.save(gameData);
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

        /*Set<PlayerData> players = gameData.getPlayers();
        List<CapitanData> capitanes = players
                                            .stream()
                                            .filter(CapitanData.class::isInstance)
                                            .map(CapitanData.class::cast)
                                            .collect(Collectors.toList());

        for(CapitanData capitan : capitanes){
            capitan.setDisciplina(capitan.getDisciplina() + capitan.getCamp().getNivel());
        }*/
    }


}
