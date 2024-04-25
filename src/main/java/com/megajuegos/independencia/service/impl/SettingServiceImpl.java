package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.auth.ManageRolesRequest;
import com.megajuegos.independencia.entities.*;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.MarketCard;
import com.megajuegos.independencia.entities.card.RepresentationCard;
import com.megajuegos.independencia.entities.data.GameData;
import com.megajuegos.independencia.entities.data.GobernadorData;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import com.megajuegos.independencia.enums.*;
import com.megajuegos.independencia.exceptions.*;
import com.megajuegos.independencia.repository.*;
import com.megajuegos.independencia.repository.data.GobernadorDataRepository;
import com.megajuegos.independencia.repository.data.PlayerDataRepository;
import com.megajuegos.independencia.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static com.megajuegos.independencia.util.Messages.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingServiceImpl implements SettingService {

    private final UserIndependenciaRepository userRepository;
    private final CityRepository cityRepository;
    private final GameDataRepository gameDataRepository;
    private final GobernadorDataRepository gobernadorDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final CongresoRepository congresoRepository;
    private final GameSubRegionRepository gameSubregionRepository;
    private final GameRegionRepository gameRegionRepository;

    @Override
    @Transactional
    public String createGame() {

        //Juego vacío
        GameData game = gameDataRepository.save(
                GameData.builder()
                        .turno(0)
                        .fase(PhaseEnum.MOVING)
                        .congresos(new ArrayList<>())
                        .build()
        );

        //Regiones (owning side) - GameData
        List<GameRegion> regions = createRegions(game);
        gameRegionRepository.saveAll(regions);
        game.setGameRegions(regions);

        //Subregiones (owning side) - Regiones
        List<GameSubRegion> subRegions = createSubregions(regions);
        gameSubregionRepository.saveAll(subRegions);
        // TODO ¿Es necesario guardarlo en GameRegion que es dependiente?

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
                .orElseThrow(() -> new PoorlyCreatedGame());

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
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_EMAIL));

        if((RoleEnum.ADMIN.equals(request.getRole()) || RoleEnum.USER.equals(request.getRole()))
                && user.getRoles().contains(request.getRole())){
            throw new WrongRoleException();
        }

        user.getRoles().add(request.getRole());
        PlayerData playerData = request.getRole().createPlayerData();
        playerData.setUser(user);
        playerData.setRol(request.getRole());

        setRevolucionarioData(playerData);

        GameData gameData = gameDataRepository.findFirstByOrderById()
                .orElseThrow(() -> new GameDataNotFoundException());

        playerData.setGameData(gameData);
        gameData.getPlayers().add(playerData);

        PlayerData playerSaved = playerDataRepository.save(playerData);
        user.getPlayerDataList().add(playerSaved);
        userRepository.save(user);

        return ROLES_ACTUALIZADOS_CON_ÉXITO;
    }

    @Override
    public String removeRoles(ManageRolesRequest request) {

        UserIndependencia user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_EMAIL));

        if(!user.getRoles().contains(request.getRole())
                || request.equals(RoleEnum.USER)){
            throw new WrongRoleException();
        }

        List<PlayerData> playerDatas = user.getPlayerDataList().stream()
                .filter(p -> request.getRole().equals(p.getRol()))
                .collect(Collectors.toList());

        playerDataRepository.deleteAll(playerDatas);
        return ROLES_ACTUALIZADOS_CON_ÉXITO;
    }

    @Override
    public City getCiudad(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new RuntimeException("La wea"));
    }


    @Override
    public String assignCity(Long toId, String ciudadNombre) throws InstanceNotFoundException {

        GobernadorData to = gobernadorDataRepository.findById(toId)
                .orElseThrow(() -> new PlayerNotFoundException());

        City city = cityRepository.findByName(ciudadNombre)
                .orElseThrow(() -> new CityNotFoundException());

        to.setCity(city);

        List<Card> cards = new ArrayList<>();
        cards.add(RepresentationCard.builder()
                        .representacion(RepresentationEnum.byNombre(ciudadNombre))
                        .build());
        for(int i = 1; i<=city.getMarketLevel();i++){
            cards.add(MarketCard.builder()
                            .level(i)
                            .nombreCiudad(ciudadNombre)
                    .build());
        }

        to.setCards(cards);
        gobernadorDataRepository.save(to);

        return CITY_ASSIGNED;
    }

    /*
    --------------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------------
    ------------------------------ Métodos privados --------------------------------------------
    --------------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------------
     */

    private List<GameRegion> createRegions(GameData game){
        List<GameRegion> gameRegions = new ArrayList<>();
        Arrays.stream(RegionEnum.values()).forEach(r ->
                gameRegions.add(GameRegion.builder()
                                    .regionEnum(r)
                                    .gameData(game)
                                    .build())
        );
        return gameRegions;
    }
    private List<GameSubRegion> createSubregions(List<GameRegion> regions){

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
    private List<City> createCities(List<GameSubRegion> subregions){

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

    private void setRevolucionarioData(PlayerData playerData){

        if(playerData instanceof RevolucionarioData){
            List<Congreso> congresos = congresoRepository.findAll();
            congresos.sort(Comparator.comparing(Congreso::getId));
            if(!congresos.isEmpty()){
                Congreso congreso = congresos.get(0);
                ((RevolucionarioData) playerData).setCongreso(congreso);
            }
        }
    }

    private List<GameSubRegion> subregionsAdyacents(List<GameSubRegion> totalList, GameSubRegion subRegion){

        return totalList.stream()
                .filter(sr -> sr.getSubRegionEnum().getAdyacentes().contains(subRegion.getSubRegionEnum()))
                .collect(Collectors.toList());
    }
}
