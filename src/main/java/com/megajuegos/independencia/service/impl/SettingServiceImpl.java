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
import com.megajuegos.independencia.exceptions.CityNotFoundException;
import com.megajuegos.independencia.exceptions.GameDataNotFoundException;
import com.megajuegos.independencia.exceptions.PlayerNotFoundException;
import com.megajuegos.independencia.exceptions.WrongRoleException;
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

    @Override
    public String createGame() {

        List<GameRegion> regions = createRegions();
        gameDataRepository.save(
                GameData.builder()
                        .turno(0)
                        .fase(PhaseEnum.MOVING)
                        .gameRegions(regions)
                        .congresos(new ArrayList<>())
                        .build()
        );

        addAdjacentSubregions();

        City sede = regions.stream()
                .map(GameRegion::getSubRegions)
                .flatMap(Collection::stream)
                .map(GameSubRegion::getCity)
                .filter(Objects::nonNull)
                .filter(c -> "Buenos Aires".equalsIgnoreCase(c.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Juego mal creado"));

        Congreso congreso = congresoRepository.save(Congreso.builder()
                                                            .plata(0)
                                                            .milicia(0)
                                                            .sede(sede)
                                                            .build());
        sede.setSedeDelCongreso(congreso);
        cityRepository.save(sede);

        GameData game = gameDataRepository.findFirstByOrderById()
                .orElseThrow(() -> new RuntimeException("Juego mal creado"));
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
    ------------------------------ Métodos privados ---------------------------------
     */

    private List<GameRegion> createRegions(){
        List<GameRegion> gameRegions = new ArrayList<>();
        Arrays.stream(RegionEnum.values()).forEach(r -> {

                    gameRegions.add(GameRegion.builder()
                            .regionEnum(r)
                            .subRegions(
                                    r.getSubRegions().stream()
                                            .map(s -> {
                                                City city = null;
                                                if (s.getCity() != null) {
                                                    city = createCityFromCityEnum(s.getCity());
                                                    cityRepository.save(city);
                                                }

                                                return GameSubRegion.builder()
                                                        .subRegionEnum(s)
                                                        .nombre(s.getNombre())
                                                        .area(s.getArea())
                                                        .color(s.getColor())
                                                        .city(city)
                                                        .adjacent(new ArrayList<>())
                                                        .build();
                                            })
                                            .collect(Collectors.toList())
                            )
                            .build());
                }
        );
        return gameRegions;
    }

    private City createCityFromCityEnum(CiudadInitEnum cityEnum) {
        return City.builder()
                .name(cityEnum.getNombre())
                .buildings(cityEnum.getEdificios())
                .diputado("")
                .taxesLevel(cityEnum.getNivelImpositivo())
                .marketLevel(cityEnum.getNivelMercado())
                .publicOpinion(cityEnum.getOpinionPublica())
                .prestige(cityEnum.getPrestigio())
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



    private void addAdjacentSubregions() {

        List<GameSubRegion> subregions = gameSubregionRepository.findAll();
        subregions.forEach(r -> r.getAdjacent().addAll(gameSubregionRepository.findAllBySubRegionEnumIn(r.getSubRegionEnum().getAdyacentes())));
        gameSubregionRepository.saveAll(subregions);
    }
}
