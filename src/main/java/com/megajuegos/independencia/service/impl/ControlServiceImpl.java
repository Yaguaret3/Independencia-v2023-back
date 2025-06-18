package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.control.*;
import com.megajuegos.independencia.dto.response.ControlResponse;
import com.megajuegos.independencia.dto.response.GameDataFullResponse;
import com.megajuegos.independencia.entities.*;
import com.megajuegos.independencia.entities.card.*;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.PhaseEnum;
import com.megajuegos.independencia.enums.RepresentationEnum;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.*;
import com.megajuegos.independencia.service.ControlService;
import com.megajuegos.independencia.service.util.GameIdUtil;
import com.megajuegos.independencia.service.util.UserUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.management.InstanceNotFoundException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.megajuegos.independencia.util.Messages.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ControlServiceImpl implements ControlService {

    private final GameDataRepository gameDataRepository;
    private final ControlDataRepository controlDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final CardRepository cardRepository;
    private final BattleRepository battleRepository;
    private final GobernadorDataRepository gobernadorRepository;
    private final CapitanDataRepository capitanRepository;
    private final RevolucionarioRepository revolucionarioRepository;
    private final UserUtil userUtil;
    private final PersonalPriceRepository priceRepository;
    private final CongresoRepository congresoRepository;
    private final CityRepository cityRepository;
    private final RouteRepository routeRepository;
    private final VoteRepository voteRepository;
    private final VotationRepository votationRepository;
    private final ArmyRepository armyRepository;
    private final GameSubRegionRepository gameSubRegionRepository;
    private final GameIdUtil gameIdUtil;
    private final BuildingRepository buildingRepository;

    private static Random random = new Random();
    private static final int DADO_CUATRO_MAX = 5;
    private static final int DADO_CUATRO_MIN = 1;
    private static final int CERO_DEFAULT = 0;
    private static final int UN_SOLO_ATACANTE = 1;

    @Override
    public String createAndGiveResourceCard(Long playerDataId, NewResourceCardRequest request) {

        PlayerData playerData = playerDataRepository.findById(playerDataId)
                .orElseThrow(PlayerNotFoundException::new);

        Card card = ResourceCard.builder()
                .resourceTypeEnum(request.getResourceType())
                .playerData(playerData)
                .build();

        cardRepository.save(card);

        playerData.getCards().add(card);
        playerDataRepository.save(playerData);
        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndGiveRepresentationCard(Long playerId, NewRepresentationCardRequest request) throws InstanceNotFoundException {
        PlayerData playerData = playerDataRepository.findById(playerId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        Card card = RepresentationCard.builder()
                .representacion(RepresentationEnum.byNombre(request.getCityName()))
                .playerData(playerData)
                .build();

        cardRepository.save(card);

        playerData.getCards().add(card);

        playerDataRepository.save(playerData);
        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndGiveMarketCard(Long playerId, NewMarketCardRequest request) {
        PlayerData playerData = playerDataRepository.findById(playerId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        Card card = MarketCard.builder()
                .nombreCiudad(cityRepository.findByName(request.getCityName())
                        .stream()
                        .filter(c -> c.getSubRegion().getGameRegion().getGameData().getId().equals(playerData.getGameData().getId()))
                        .findFirst()
                        .orElseThrow(() -> new CityNotFoundException(request.getCityName())).getName())
                .level(request.getLevel())
                .playerData(playerData)
                .build();

        cardRepository.save(card);

        playerData.getCards().add(card);

        playerDataRepository.save(playerData);
        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndGiveExtraCard(Long playerId, ExtraCardRequest request) {

        PlayerData playerData = playerDataRepository.findById(playerId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        Card card = ExtraCard.builder()
                .bonificacion(request.getBonificacion())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .playerData(playerData)
                .build();

        cardRepository.save(card);

        playerData.getCards().add(card);

        playerDataRepository.save(playerData);
        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndGiveActionCard(Long playerId, NewActionCardRequest request) {
        PlayerData playerData = playerDataRepository.findById(playerId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        Card card = ActionCard.builder()
                .tipoAccion(request.getActionType())
                .playerData(playerData)
                .build();

        cardRepository.save(card);
        playerData.getCards().add(card);
        playerDataRepository.save(playerData);
        return CARD_CREATED_GIVEN;
    }

    @Override
    public String createAndGiveBattleCard(Long playerId, NewBattleCardRequest request) {
        PlayerData playerData = playerDataRepository.findById(playerId)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_ID));

        Card card = BattleCard.builder()
                .tipoOrdenDeBatalla(request.getBattleType())
                .playerData(playerData)
                .build();

        cardRepository.save(card);
        playerData.getCards().add(card);
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
        if (!optional.isPresent() || !(optional.get() instanceof RevolucionarioData)) {
            throw new PlayerNotFoundException(revolucionarioId);
        }
        revolucionario = (RevolucionarioData) optional.get();

        Congreso congreso = revolucionario.getCongreso();

        congreso.getRevolucionarios().forEach(r -> {
            if (r.equals(revolucionario)) {
                r.setPresidente(true);
            } else {
                r.setPresidente(false);
            }
        });

        revolucionarioRepository.saveAll(congreso.getRevolucionarios());

        return CONGRESS_PRESIDENT_ASSIGNED;
    }

    @Override
    public String concludePhase() {
        ControlData controlData = getControlDataFromLoggedUser();
        GameData gameData = controlData.getGameData();

        if (controlData.getSiguienteFaseSolicitada()) {
            controlData.setSiguienteFaseSolicitada(false);
            controlDataRepository.save(controlData);
            return CONCLUDE_PHASE_CANCELLED;
        }

        controlData.setSiguienteFaseSolicitada(true);

        List<ControlData> controles = gameData.getPlayers().stream()
                .filter(ControlData.class::isInstance)
                .map(p -> (ControlData) p)
                .collect(Collectors.toList());

        List<ControlData> controlesQueFaltanTerminar = controles.stream()
                .filter(c -> !c.getSiguienteFaseSolicitada())
                .collect(Collectors.toList());

        if (controlesQueFaltanTerminar.isEmpty()) {
            switch (gameData.getFase()) {
                case MOVING:
                    gameData.setFase(PhaseEnum.PLANNING);
                    break;
                case PLANNING:
                    gameData.setFase(PhaseEnum.REVEALING);
                    break;
                case REVEALING:
                    advanceTurn(gameData);
                    gameData.setFase(PhaseEnum.MOVING);
                    break;
            }
            gameDataRepository.save(gameData);
            controles.forEach(c -> c.setSiguienteFaseSolicitada(false));
            controlDataRepository.saveAll(controles);
            return PHASE_CONCLUDED;
        }

        controlDataRepository.save(controlData);
        return CONCLUDE_PHASE_REQUESTED;
    }

    @Override
    public String moveCard(Long fromId, Long toId, Long cartaId) {
        PlayerData from = playerDataRepository.findById(fromId)
                .orElseThrow(PlayerNotFoundException::new);

        PlayerData to = playerDataRepository.findById(toId)
                .orElseThrow(PlayerNotFoundException::new);

        Card card = null;
        for (Card c : from.getCards()) {
            if (Objects.equals(c.getId(), cartaId)) {
                card = c;
                break;
            }
        }
        if (card != null) {
            to.getCards().add(card);
            from.getCards().remove(card);
        }
        playerDataRepository.save(from);
        playerDataRepository.save(to);

        return CARD_MOVED;
    }

    @Override
    public String removeCard(Long idCard) {
        Card card = cardRepository.findById(idCard).orElseThrow(() -> new CardNotFoundException(idCard));
        PlayerData playerData = card.getPlayerData();
        playerData.getCards().remove(card);
        playerDataRepository.save(playerData);
        cardRepository.delete(card);
        return CARD_REMOVED;
    }

    @Override
    public GameDataFullResponse getFullData() {

        ControlData controlData = getControlDataFromLoggedUser();

        GameData gameData = controlData.getGameData();

        List<Route> rutas = routeRepository.findByTurn(gameData.getTurno());

        List<Battle> batallas = battleRepository.findByActive(true);

        return GameDataFullResponse.toFullResponse(gameData, rutas, batallas);
    }

    @Override
    public ControlResponse getControlData() {
        ControlData controlData = getControlDataFromLoggedUser();

        return ControlResponse.toDto(controlData);
    }

    @Override
    public String editCity(Map<String, Integer> request, Long id) {

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(id));
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
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        Building building = city.getBuildings().stream().filter(b -> b.getId().equals(buildingId)).findFirst().orElseThrow(() -> new BuildingNotFoundException(buildingId));

        city.getBuildings().remove(building);
        cityRepository.save(city);
        buildingRepository.delete(building);

        return BUILDING_REMOVED;
    }

    @Override
    public String addBuilding(Long cityId, NewBuildingRequest request) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        Building building = Building.builder()
                .buildingType(request.getBuildingType())
                .city(city)
                .build();

        buildingRepository.save(building);

        city.getBuildings().add(building);
        cityRepository.save(city);


        return BUILDING_CREATED;
    }

    @Override
    public String updateRoute(Long routeId, AssignRouteValueRequest request) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new RouteNotFoundException(routeId));
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
        Votation votation = votationRepository.findById(votationId).orElseThrow(() -> new VotationNotFoundException(votationId));
        if (request.getPropuesta() != null) {
            votation.setPropuesta(request.getPropuesta());
        }
        if (request.getActive() != null) {
            votation.setActive(request.getActive());
        }
        return VOTATION_UPDATED;
    }

    @Override
    public String addVote(Long votationId, NewVoteRequest request) {
        Votation votation = votationRepository.findById(votationId).orElseThrow(() -> new VotationNotFoundException(votationId));
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
        Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new VoteNotFoundException(voteId));
        vote.setVoteType(request.getVoteType());
        voteRepository.save(vote);
        return VOTE_UPDATED;
    }

    @Override
    public String createBattle(CreateBattleRequest request) {

        if (request.getCombatientes().size() <= 1) {
            throw new IncorrectBattleException();
        }

        ControlData controlData = getControlDataFromLoggedUser();
        GameData gameData = controlData.getGameData();

        GameSubRegion gameSubRegionInvolucrada = gameSubRegionRepository.findById(request.getGameSubRegionId())
                .orElseThrow(() -> new SubRegionNotFoundException(request.getGameSubRegionId()));

        List<CapitanData> capitanesInvolucrados = capitanRepository.findAllById(request.getCombatientes().stream()
                .map(ArmyForBattleRequest::getId)
                .collect(Collectors.toList()));

        List<CapitanData> capitanesEnSubregion = gameSubRegionInvolucrada.getEjercitos().stream()
                .map(Army::getCapitanData)
                .collect(Collectors.toList());

        if (!new HashSet<>(capitanesEnSubregion).containsAll(capitanesInvolucrados)) {
            throw new IncorrectBattleException();
        }

        List<Army> ejercitosInvolucrados = capitanesInvolucrados.stream()
                .flatMap(c -> c.getEjercito().stream())
                .filter(a -> request.getGameSubRegionId().equals(a.getSubregion().getId()))
                .collect(Collectors.toList());

        ejercitosInvolucrados.forEach(e -> {
            Optional<ArmyForBattleRequest> requestedArmyId = request.getCombatientes().stream()
                    .filter(a -> a.getId().equals(e.getCapitanData().getId()))
                    .findFirst();
            e.setAtaque(requestedArmyId.get().isAtaque());
            e.setMilicias(CERO_DEFAULT);
        });

        Battle battle = Battle.builder()
                .active(true)
                .turnoDeJuego(gameData.getTurno())
                .subregion(gameSubRegionInvolucrada)
                .combatientes(ejercitosInvolucrados)
                .build();
        battleRepository.save(battle);

        ejercitosInvolucrados.forEach(e -> e.setBattle(battle));

        armyRepository.saveAll(ejercitosInvolucrados);

        return BATTLE_CREATED;
    }

    @Override
    public String assignRandomValuesBattle(Long battleId) {

        //Busca batallas y toma ejércitos involucrados
        Battle battle = battleRepository.findById(battleId).orElseThrow(() -> new BattleNotFoundException(battleId));
        List<Army> ejercitosInvolucrados = battle.getCombatientes();

        List<Integer> valoresAzar = new ArrayList<>();

        //Asigna iniciativa random y tira los dados
        ejercitosInvolucrados.forEach(e -> {
            e.setIniciativa(random.nextInt());
            valoresAzar.add(random.nextInt(DADO_CUATRO_MAX - DADO_CUATRO_MIN) + DADO_CUATRO_MIN);
        });

        //Ordena para hacer coincidir los ejércitos que atacan con los dados mayores
        ejercitosInvolucrados.sort(Comparator.comparing(Army::isAtaque, Comparator.reverseOrder()).thenComparing(Army::getIniciativa));
        valoresAzar.sort(Comparator.reverseOrder());

        //Asigna valores
        int index = 0;
        while (index < ejercitosInvolucrados.size()) {
            Army ejercito = ejercitosInvolucrados.get(index);
            ejercito.setValorAzar(valoresAzar.get(index));
            ejercito.setValorProvisorio(ejercito.getValorAzar() + ejercito.getMilicias());
            index++;
        }

        battleRepository.save(battle);
        return BATTLE_VALUES_ASSIGNED;
    }

    @Override
    public String assignReserve(Long playerId, Integer militia) {
        PlayerData playerData = playerDataRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException(playerId));
        if (playerData instanceof CapitanData) {
            CapitanData capitanData = (CapitanData) playerData;
            capitanData.setReserva(militia);
            playerDataRepository.save(capitanData);
        }
        if (playerData instanceof GobernadorData) {
            GobernadorData gobernadorData = (GobernadorData) playerData;
            gobernadorData.setMilicia(militia);
            playerDataRepository.save(gobernadorData);
        }

        return RESERVE_ASSIGNED;
    }

    @Override
    public String deleteArmy(Long armyId) {
        Army army = armyRepository.findById(armyId).orElseThrow(() -> new ArmyNotFoundException(armyId));

        CapitanData capitanData = army.getCapitanData();
        capitanData.getEjercito().remove(army);

        capitanRepository.save(capitanData);
        armyRepository.deleteById(armyId);

        return ARMY_DELETED;
    }

    @Override
    public String createArmy(NewArmyRequest request) {
        CapitanData capitanData = capitanRepository.findById(request.getCapitanId()).orElseThrow(() -> new PlayerNotFoundException(request.getCapitanId()));

        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getGameSubRegionId()).orElseThrow(() -> new SubRegionNotFoundException(request.getGameSubRegionId()));

        Army army = Army.builder()
                .subregion(gameSubRegion)
                .capitanData(capitanData)
                .build();

        armyRepository.save(army);
        capitanData.getEjercito().add(army);
        capitanRepository.save(capitanData);
        return ARMY_CREATED;
    }

    @Override
    public String moveCamp(MoveCampRequest request) {

        CapitanData capitanData = capitanRepository.findById(request.getCapitanId()).orElseThrow(() -> new PlayerNotFoundException(request.getCapitanId()));
        GameSubRegion gameSubRegion = gameSubRegionRepository.findById(request.getGameSubregionId()).orElseThrow(() -> new SubRegionNotFoundException(request.getGameSubregionId()));

        Camp camp = capitanData.getCamp() == null ? Camp.builder()
                .nivel(1)
                .capitanData(capitanData)
                .build()
                : capitanData.getCamp();

        camp.setSubregion(gameSubRegion);
        camp.setGameRegion(gameSubRegion.getGameRegion());

        capitanRepository.save(capitanData);
        return CAMP_MOVED;
    }

    @Override
    public String assignNewDiputadoToCity(Long cityId, Long diputadoId) throws InstanceNotFoundException {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
        RevolucionarioData revolucionarioData = (RevolucionarioData) playerDataRepository.findById(diputadoId)
                .orElseThrow(PlayerNotFoundException::new);

        List<RepresentationCard> cards = cardRepository
                .findRepresentationCardByCity(RepresentationEnum.byNombre(city.getName()))
                .stream()
                .filter(r -> !r.isAlreadyPlayed())
                .collect(Collectors.toList());

        if (cards.isEmpty()) {
            throw new CardNotFoundException(0L);
        }
        if (cards.size() > 1) {
            throw new MoreThanOneRepresentationCardPerCity(city.getName());
        }

        RepresentationCard representationCard = cards.get(0);
        PlayerData actualPlayer = representationCard.getPlayerData();
        actualPlayer.getCards().remove(representationCard);
        revolucionarioData.getCards().add(representationCard);

        city.setDiputado(revolucionarioData.getUser().getUsername());

        cityRepository.save(city);
        playerDataRepository.save(actualPlayer);
        playerDataRepository.save(revolucionarioData);

        return NEW_DIPUTADO_ASSIGNED;
    }

    @Override
    public String addPlata(Long playerId, Integer plata) {
        PlayerData playerData = playerDataRepository.findById(playerId)
                .orElseThrow(PlayerNotFoundException::new);

        if (playerData instanceof GobernadorData) {
            GobernadorData gobernadorData = (GobernadorData) playerData;
            gobernadorData.setPlata(plata);
            playerDataRepository.save(gobernadorData);
        }
        if (playerData instanceof RevolucionarioData) {
            RevolucionarioData revolucionarioData = (RevolucionarioData) playerData;
            revolucionarioData.setPlata(plata);
            playerDataRepository.save(revolucionarioData);
        }
        return PLATA_ASSIGNED;
    }

    @Override
    public String removeCongress(Long congresoId) {
        Congreso congreso = congresoRepository.findById(congresoId)
                .orElseThrow(() -> new CongresoNotFoundException(congresoId));
        List<RevolucionarioData> revolucionarios = congreso.getRevolucionarios();
        City sede = congreso.getSede();

        revolucionarios.forEach(r -> r.setCongreso(null));
        sede.setSedeDelCongreso(null);

        List<Votation> votaciones = congreso.getVotations();
        votaciones.forEach(v -> v.setCongreso(null));

        revolucionarioRepository.saveAll(revolucionarios);
        cityRepository.save(sede);

        congresoRepository.deleteById(congresoId);
        return CONGRESS_REMOVED;
    }

    @Override
    public String updateCongress(Long congresoId, UpdateCongressRequest request) {

        Congreso congreso = congresoRepository.findById(congresoId)
                .orElseThrow(() -> new CongresoNotFoundException(congresoId));
        if (request.getMilicia() != null) {
            congreso.setMilicia(request.getMilicia());
        }
        if (request.getPlata() != null) {
            congreso.setPlata(request.getPlata());
        }
        congresoRepository.save(congreso);
        congreso.getRevolucionarios().forEach(r -> {
            if (r.getId().equals(request.getPresidente())) {
                r.setPresidente(true);
            } else {
                r.setPresidente(false);
            }
        });
        revolucionarioRepository.saveAll(congreso.getRevolucionarios());

        return CONGRESS_UPDATED;
    }

    @Override
    public String createNewCongress(CreateNewCongressRequest request) {

        City sede = cityRepository.findById(request.getSedeId())
                .orElseThrow(() -> new CityNotFoundException(request.getSedeId()));
        List<RevolucionarioData> revolucionarios = revolucionarioRepository.findAllById(request.getDiputadosIds());

        GameData gameData = gameDataRepository.findFirstByOrderByIdDesc()
                .orElseThrow(GameDataNotFoundException::new);

        Congreso congreso = Congreso.builder()
                .sede(sede)
                .revolucionarios(revolucionarios)
                .plata(request.getPlata())
                .milicia(request.getMilicia())
                .gameData(gameData)
                .build();

        sede.setSedeDelCongreso(congreso);
        revolucionarios.forEach(r -> r.setCongreso(congreso));

        congresoRepository.save(congreso);
        cityRepository.save(sede);

        congresoRepository.save(congreso);
        congreso.getRevolucionarios().forEach(r -> r.setPresidente(r.getId().equals(request.getPresidenteId())));
        revolucionarioRepository.saveAll(revolucionarios);

        gameData.getCongresos().add(congreso);
        gameDataRepository.save(gameData);
        return CONGRESS_CREATED;
    }

    @Override
    public String moveToCongress(MoveToCongressRequest request) {

        Congreso congreso = congresoRepository.findById(request.getCongresoId())
                .orElseThrow(() -> new CongresoNotFoundException(request.getCongresoId()));
        RevolucionarioData revolucionarioData = revolucionarioRepository.findById(request.getRevolucionarioId())
                .orElseThrow(() -> new PlayerNotFoundException(request.getRevolucionarioId()));

        Congreso congresoOld = revolucionarioData.getCongreso();
        congresoOld.getRevolucionarios().remove(revolucionarioData);
        congreso.getRevolucionarios().add(revolucionarioData);

        revolucionarioData.setCongreso(congreso);

        congresoRepository.saveAll(Arrays.asList(congreso, congresoOld));
        revolucionarioRepository.save(revolucionarioData);

        return MOVED_TO_CONGRESS;
    }

    @Override
    public String solveBattle(SolveBattleRequest request) {

       Battle battle = battleRepository.findById(request.getBattleId()).orElseThrow(() -> new BattleNotFoundException(request.getBattleId()));
        GameSubRegion gameSubRegion = battle.getSubregion();

        List<Army> ejercitos = battle.getCombatientes();

        ejercitos.forEach(army -> {

            request.getResultados().forEach(r -> {

                if (Objects.equals(army.getId(), r.getArmyId()) && r.isDestruido()) {

                    //Si el ejército fue derrotado...
                    CapitanData capitanData = army.getCapitanData();
                    //Resta milicias a la reserva del capitán.
                    capitanData.setReserva(capitanData.getReserva() - r.getMiliciasPerdidas());
                    //Quita el ejército de la subregión (y del capitán)
                    capitanData.getEjercito().remove(army);
                    gameSubRegion.getEjercitos().remove(army);
                    // Borra cartas asociadas al ejército (orphan removal)
                    cardRepository.deleteAll(army.getCartasJugadas());
                    armyRepository.delete(army);

                }
                if (Objects.equals(army.getId(), r.getArmyId()) && !r.isDestruido()) {
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
            cardRepository.deleteAll(army.getCartasJugadas());
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

        gameData.setTurno(gameData.getTurno() + 1);
        gameData.setNextEndOfTurn(newTurnInMillis);

        if(gameData.getTurno().equals(0)){
            gameDataRepository.save(gameData);
            return;
        }
        //Gobernadores TODO Leyes políticas impositivas
        deshabilitarMercadosNoUsados(gameData);
        recolectarImpuestosRecuperarRepresentacionYMercados(gameData);
        //Mercaderes TODO Leyes comerciales
        sumarPuntajeComercialAcumulado(gameData);
        //Revolucionarios
        limpiarRepresentacion(gameData);
        //Capitanes
        // ???

        //Verificar que se están grabando las cartas de los jugadores
        playerDataRepository.saveAll(gameData.getPlayers());
        gameDataRepository.save(gameData);
    }

    private void deshabilitarMercadosNoUsados(GameData gameData) {

        List<MarketCard> markets = gameData.getPlayers()
                .stream()
                .flatMap(p -> p.getCards().stream())
                .filter(MarketCard.class::isInstance)
                .map(MarketCard.class::cast)
                .collect(Collectors.toList());

        markets.forEach(m -> m.setAlreadyPlayed(true));

        cardRepository.saveAll(markets);

    }

    private void recolectarImpuestosRecuperarRepresentacionYMercados(GameData gameData) {

        List<GobernadorData> gobernadores = gameData.getPlayers()
                .stream()
                .filter(GobernadorData.class::isInstance)
                .map(GobernadorData.class::cast)
                .collect(Collectors.toList());

        gobernadores.forEach(g -> {

            //Impuestos
            g.setPlata(g.getPlata() + g.getCity().getTaxesLevel());

            //Representacion
            try {
                RepresentationCard representationCard = RepresentationCard.builder()
                        .representacion(RepresentationEnum.byNombre(g.getCity().getName()))
                        .playerData(g)
                        .build();
                cardRepository.save(representationCard);
                g.getCards().add(representationCard);
            } catch (InstanceNotFoundException e) {
                throw new RuntimeException(e);
            }

            //Mercados
            IntStream.range(0, g.getCity().getMarketLevel()).forEach(i -> {
                MarketCard marketCard = MarketCard.builder()
                        .playerData(g)
                        .level(i + 1)
                        .nombreCiudad(g.getCity().getName())
                        .build();
                cardRepository.save(marketCard);
                g.getCards().add(marketCard);
            });
        });
    }

    private void sumarPuntajeComercialAcumulado(GameData gameData) {
        List<MercaderData> mercaderes = gameData.getPlayers().stream()
                .filter(MercaderData.class::isInstance)
                .map(MercaderData.class::cast)
                .collect(Collectors.toList());

        mercaderes.forEach(m -> {

            m.setPuntajeComercial(m.getPuntajeComercial() + m.getRoutes().stream()
                    .filter(r -> r.getTurn().equals(gameData.getTurno()-1))
                    .map(Route::getTradeScore)
                    .mapToInt(Long::intValue)
                    .sum()
            );
            m.setPuntajeComercialAcumulado(m.getPuntajeComercialAcumulado() + m.getPuntajeComercial());
        });
    }

    private void limpiarRepresentacion(GameData gameData) {
        List<RevolucionarioData> revolucionarios = gameData.getPlayers().stream()
                .filter(RevolucionarioData.class::isInstance)
                .map(RevolucionarioData.class::cast)
                .collect(Collectors.toList());

        List<RepresentationCard> representationCard = revolucionarios.stream()
                .map(PlayerData::getCards)
                .filter(RepresentationCard.class::isInstance)
                .map(RepresentationCard.class::cast)
                .collect(Collectors.toList());

        representationCard.forEach(c -> c.setPlayerData(null));

        revolucionarios.forEach(r -> r.setCards(r.getCards().stream()
                .filter(c -> !(c instanceof RepresentationCard))
                .collect(Collectors.toList())
        ));

        cardRepository.saveAll(representationCard);
    }

    private ControlData getControlDataFromLoggedUser() {
        Long playerId = userUtil.getCurrentUser().getPlayerDataList().stream()
                .filter(p -> Objects.equals(gameIdUtil.currentGameId(), p.getGameData().getId()))
                .findFirst()
                .map(PlayerData::getId)
                .orElseThrow(PlayerNotFoundException::new);
        return controlDataRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));
    }
}
