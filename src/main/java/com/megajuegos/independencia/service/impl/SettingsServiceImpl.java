package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.settings.AssignCityRequest;
import com.megajuegos.independencia.dto.request.settings.CreateGameRequest;
import com.megajuegos.independencia.dto.request.settings.ManageRolesRequest;
import com.megajuegos.independencia.dto.request.settings.UpdateUsernameRequest;
import com.megajuegos.independencia.dto.response.settings.SettingsCityResponse;
import com.megajuegos.independencia.dto.response.settings.SettingsGameDataResponse;
import com.megajuegos.independencia.dto.response.settings.SettingsUserResponse;
import com.megajuegos.independencia.entities.*;
import com.megajuegos.independencia.entities.card.*;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.*;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.GobernadorDataRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.megajuegos.independencia.util.Messages.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SettingsServiceImpl implements SettingsService {

    private final UserIndependenciaRepository userRepository;
    private final CityRepository cityRepository;
    private final GameDataRepository gameDataRepository;
    private final GobernadorDataRepository gobernadorDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final CongresoRepository congresoRepository;
    private final GameSubRegionRepository gameSubregionRepository;
    private final GameRegionRepository gameRegionRepository;
    private final CampRepository campRepository;
    private final PersonalPriceRepository personalPriceRepository;
    private final LogRepository logRepository;
    private final CardRepository cardRepository;
    private final RouteRepository routeRepository;
    private final ArmyRepository armyRepository;
    private final ControlServiceImpl controlServiceImpl;

    @Override
    public String createGame(CreateGameRequest request) {

        //Juego vacío
        GameData game = gameDataRepository.save(
                GameData.builder()
                        .nombre(request.getNombre())
                        .turno(0)
                        .fase(PhaseEnum.REVEALING)
                        .congresos(new ArrayList<>())
                        .active(true)
                        .createdOn(Calendar.getInstance().toInstant())
                        .build()
        );

        //Regiones (owning side) - GameData
        List<GameRegion> regions = createRegions(game);
        gameRegionRepository.saveAll(regions);
        game.setGameRegions(regions);

        //Subregiones (owning side) - Regiones
        List<GameSubRegion> subRegions = createSubregions(regions);
        gameSubregionRepository.saveAll(subRegions);

        //Ciudades - Subregiones (owning side)
        List<City> ciudades = cityRepository.saveAll(createCities(subRegions));
        ciudades.forEach(c -> {
            GameSubRegion gameSubRegion = c.getSubRegion();
            gameSubRegion.setCity(c);
            gameSubregionRepository.save(gameSubRegion);
        });

        //Subregions adyacentes
        subRegions.forEach(r -> r.getAdjacent().addAll(subregionsAdyacents(subRegions, r)));
        gameSubregionRepository.saveAll(subRegions);

        //Congreso (owning side) - Ciudad
        City sede = ciudades.stream().filter(c -> c.getSubRegion().getSubRegionEnum().equals(SubRegionEnum.BUENOS_AIRES))
                .findFirst()
                .orElseThrow(PoorlyCreatedGame::new);

        Congreso congreso = congresoRepository.save(Congreso.builder()
                .plata(0)
                .milicia(0)
                .sede(sede)
                .gameData(game)
                .build());

        sede.setSedeDelCongreso(congreso);
        cityRepository.save(sede);

        congresoRepository.save(congreso);

        // Congreso (owning side)- GameData
        game.getCongresos().add(congreso);
        gameDataRepository.save(game);

        return NEW_GAME_CREATED;
    }

    @Override
    public String addRoles(ManageRolesRequest request) {

        UserIndependencia user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UserIndependenciaNotFound(request.getId()));

        if ((RoleEnum.ADMIN.equals(request.getRole()) || RoleEnum.USER.equals(request.getRole()))
                || user.getRoles().contains(request.getRole())) {
            throw new WrongRoleException();
        }

        user.getRoles().add(request.getRole());
        PlayerData playerData = request.getRole().createPlayerData();
        playerData.setUser(user);
        playerData.setRol(request.getRole());
        playerDataRepository.save(playerData); //Persistencia primaria

        //setByRole
        setRevolucionarioData(playerData);
        setCapitanData(playerData);

        GameData gameData = gameDataRepository.findFirstByOrderByIdDesc()
                .orElseThrow(GameDataNotFoundException::new);

        playerData.setGameData(gameData);
        gameData.getPlayers().add(playerData);

        setPrecios(playerData);

        playerDataRepository.save(playerData); //Persistencia editada
        user.getPlayerDataList().add(playerData);
        userRepository.save(user);

        return ROLES_ACTUALIZADOS_CON_EXITO;
    }

    @Override
    public String removeRoles(ManageRolesRequest request) {

        UserIndependencia user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UserIndependenciaNotFound(request.getId()));

        PlayerData playerData = user.getPlayerDataList().stream().findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(String.format(NO_PLAYER_DATA_FOR_USER, user.getUsername())));
        Long gameDataId = playerData.getGameData().getId();

        user.getRoles().forEach(r -> log.info(r.name()));

        if (!user.getRoles().contains(request.getRole())) {
            throw new WrongRoleException();
        }

        // BEGIN - Constraints from other tables (orphan removal? cascade?)
        List<PlayerData> playerDatas = user.getPlayerDataList().stream()
                .filter(p -> request.getRole().equals(p.getRol()))
                .filter(p -> p.getGameData().getId().equals(gameDataId))
                .collect(Collectors.toList());

        List<PersonalPrice> prices = playerDatas.stream()
                .flatMap(p -> p.getPrices().stream())
                .collect(Collectors.toList());
        List<Log> logs = playerDatas.stream()
                .flatMap(p -> p.getLogs().stream())
                .collect(Collectors.toList());
        List<Card> cards = playerDatas.stream()
                .flatMap(p -> p.getCards().stream())
                .collect(Collectors.toList());
        List<Route> routes = playerDatas.stream()
                .filter(MercaderData.class::isInstance)
                .map(p -> (MercaderData) p)
                .flatMap(p -> p.getRoutes().stream())
                .collect(Collectors.toList());

        if (playerData instanceof CapitanData) {
            Camp camp = ((CapitanData) playerData).getCamp();
            campRepository.delete(camp);
            List<Army> armies = ((CapitanData) playerData).getEjercito();
            armyRepository.deleteAll(armies);
        }
        personalPriceRepository.deleteAll(prices);
        logRepository.deleteAll(logs);
        cardRepository.deleteAll(cards);
        routeRepository.deleteAll(routes);
        // END - Constraints from other tables (orphan removal? cascade?)

        playerDataRepository.deleteAll(playerDatas);

        if (playerData instanceof GobernadorData) {
            City city = ((GobernadorData) playerData).getCity();
            if (city != null) {
                city.setGobernadorData(null);
                cityRepository.save(city);
            }
        }

        user.getRoles().remove(request.getRole());
        userRepository.save(user);

        return ROLES_ACTUALIZADOS_CON_EXITO;
    }

    @Override
    public City getCiudad(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new RuntimeException("La wea"));
    }


    @Override
    public String assignCity(AssignCityRequest request) throws InstanceNotFoundException {

        UserIndependencia user = userRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new UserIndependenciaNotFound(request.getPlayerId()));

        GobernadorData to = user.getPlayerDataList().stream()
                .filter(p -> p.getGameData().isActive())
                .filter(GobernadorData.class::isInstance)
                .map(p -> (GobernadorData) p)
                .findFirst()
                .orElseThrow(WrongRoleException::new);

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new CityNotFoundException(request.getCityId()));

        if (city.getGobernadorData() != null) {
            throw new CityAlreadyHasGovernorException(city.getName(), city.getGobernadorData().getUser().getUsername());
        }

        to.setCity(city);

        List<Card> cards = new ArrayList<>();
        cards.add(RepresentationCard.builder()
                .representacion(RepresentationEnum.byNombre(city.getName()))
                .playerData(to)
                .city(city)
                .build());
        for (int i = 1; i <= city.getMarketLevel(); i++) {
            cards.add(MarketCard.builder()
                    .level(i)
                    .nombreCiudad(city.getName())
                    .playerData(to)
                    .build());
        }
        cardRepository.saveAll(cards);
        to.setCards(cards);
        gobernadorDataRepository.save(to);

        return CITY_ASSIGNED;
    }

    @Override
    public List<SettingsGameDataResponse> getGames() {
        return gameDataRepository.findAll().stream()
                .map(SettingsGameDataResponse::toSettingsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String deactivateGame(Long id) {
        GameData gameData = gameDataRepository.findById(id)
                .orElseThrow(() -> new GameDataNotFoundException(id));
        gameData.setActive(false);
        gameDataRepository.save(gameData);
        return GAME_DEACTIVATED;
    }

    @Override
    public String deleteGame(Long id) {
        gameDataRepository.deleteById(id);
        return GAME_DELETED;
    }

    @Override
    public List<SettingsUserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(SettingsUserResponse::toSettingsResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SettingsCityResponse> getCities() {
        GameData gameData = gameDataRepository.findFirstByOrderByIdDesc()
                .filter(GameData::isActive)
                .orElseThrow(GameDataNotFoundException::new);

        return gameData.getGameRegions()
                .stream()
                .flatMap(r -> r.getSubRegions().stream())
                .map(GameSubRegion::getCity)
                .filter(Objects::nonNull)
                .map(SettingsCityResponse::toSettingsReponse)
                .collect(Collectors.toList());
    }

    @Override
    public String updateUsername(UpdateUsernameRequest request) {
        UserIndependencia user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UserIndependenciaNotFound(request.getId()));
        user.setUsername(request.getUsername());
        userRepository.save(user);
        return USERNAME_UPDATED;
    }

    @Override
    public String forceConcludePhase() {
        GameData gameData = gameDataRepository.findFirstByOrderByIdDesc().orElseThrow(GameDataNotFoundException::new);
        List<ControlData> controles = gameData.getPlayers().stream()
                .filter(c -> c instanceof ControlData)
                .map(ControlData.class::cast)
                .collect(Collectors.toList());
        controlServiceImpl.doConcludePhase(gameData, controles);
        return PHASE_CONCLUDED;
    }

    /*
    --------------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------------
    ------------------------------ Métodos privados --------------------------------------------
    --------------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------------
     */

    private List<GameRegion> createRegions(GameData game) {
        List<GameRegion> gameRegions = new ArrayList<>();
        Arrays.stream(RegionEnum.values()).forEach(r ->
                gameRegions.add(GameRegion.builder()
                        .regionEnum(r)
                        .gameData(game)
                        .build())
        );
        return gameRegions;
    }

    private List<GameSubRegion> createSubregions(List<GameRegion> regions) {

        List<GameSubRegion> gameSubregions = new ArrayList<>();

        regions.forEach(r -> gameSubregions.addAll(
                r.getRegionEnum().getSubRegions().stream()
                        .map(s -> GameSubRegion.builder()
                                .subRegionEnum(s)
                                .nombre(s.getNombre())
                                .area(s.getArea())
                                .color(s.getColor())
                                .adjacent(new ArrayList<>())
                                .gameRegion(r)
                                .build())
                        .collect(Collectors.toList())));
        return gameSubregions;

    }

    private List<City> createCities(List<GameSubRegion> subregions) {

        return subregions.stream()
                .filter(sr -> sr.getSubRegionEnum().getCity() != null)
                .map(this::createCityFromGameSubRegion)
                .collect(Collectors.toList());
    }

    private City createCityFromGameSubRegion(GameSubRegion gameSubRegion) {
        return City.builder()
                .name(gameSubRegion.getSubRegionEnum().getCity().getNombre())
                .buildings(gameSubRegion.getSubRegionEnum().getCity().getEdificios())
                .diputado("")
                .taxesLevel(gameSubRegion.getSubRegionEnum().getCity().getNivelImpositivo())
                .marketLevel(gameSubRegion.getSubRegionEnum().getCity().getNivelMercado())
                .publicOpinion(gameSubRegion.getSubRegionEnum().getCity().getOpinionPublica())
                .prestige(gameSubRegion.getSubRegionEnum().getCity().getPrestigio())
                .subRegion(gameSubRegion)
                .build();
    }

    private List<GameSubRegion> subregionsAdyacents(List<GameSubRegion> totalList, GameSubRegion subRegion) {

        return totalList.stream()
                .filter(sr -> sr.getSubRegionEnum().getAdyacentes().contains(subRegion.getSubRegionEnum()))
                .collect(Collectors.toList());
    }

    private void setRevolucionarioData(PlayerData playerData) {

        if (playerData instanceof RevolucionarioData) {
            List<Congreso> congresos = congresoRepository.findAll();
            congresos.sort(Comparator.comparing(Congreso::getId));
            if (!congresos.isEmpty()) {
                Congreso congreso = congresos.get(0);
                ((RevolucionarioData) playerData).setCongreso(congreso);
            }
        }
    }

    private void setCapitanData(PlayerData playerData) {

        if (playerData instanceof CapitanData) {

            List<GameSubRegion> subregionList = gameSubregionRepository.findAllBySubRegionEnumIn(Collections.singletonList(SubRegionEnum.BUENOS_AIRES));

            Camp camp = campRepository.save(Camp.builder()
                    .nivel(1)
                    .subregion(subregionList.get(0))
                    .gameRegion(subregionList.get(0).getGameRegion())
                    .build());

            ((CapitanData) playerData).setCamp(camp);

            cardRepository.saveAll(Arrays.asList(
                    ResourceCard.builder().resourceTypeEnum(ResourceTypeEnum.METALMECANICA).build(),
                    ResourceCard.builder().resourceTypeEnum(ResourceTypeEnum.METALMECANICA).build(),
                    ActionCard.builder().tipoAccion(ActionTypeEnum.DESPLIEGUE).playerData(playerData).build(),
                    ActionCard.builder().tipoAccion(ActionTypeEnum.DESPLIEGUE).playerData(playerData).build(),
                    ActionCard.builder().tipoAccion(ActionTypeEnum.DEFENSA).playerData(playerData).build(),
                    ActionCard.builder().tipoAccion(ActionTypeEnum.ATAQUE).playerData(playerData).build()));
        }
    }

    private void setPrecios(PlayerData playerData) {

        if (!(playerData instanceof ControlData)) {
            List<PersonalPrice> prices = playerData.getRol().getInitialPrices();
            prices.forEach(p -> p.setPlayerData(playerData));
            personalPriceRepository.saveAll(prices);
        }
    }
}
