package com.megajuegos.independencia.service.impl;

import com.megajuegos.independencia.dto.request.auth.ManageRolesRequest;
import com.megajuegos.independencia.entities.*;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.MarketCard;
import com.megajuegos.independencia.entities.card.RepresentationCard;
import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.CiudadInitEnum;
import com.megajuegos.independencia.enums.RegionEnum;
import com.megajuegos.independencia.enums.RepresentationEnum;
import com.megajuegos.independencia.exceptions.CityNotFoundException;
import com.megajuegos.independencia.exceptions.GameDataNotFoundException;
import com.megajuegos.independencia.exceptions.PlayerNotFoundException;
import com.megajuegos.independencia.repository.CityRepository;
import com.megajuegos.independencia.repository.CongresoRepository;
import com.megajuegos.independencia.repository.GameDataRepository;
import com.megajuegos.independencia.repository.UserIndependenciaRepository;
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
public class SettingServiceImpl implements SettingService {

    private final UserIndependenciaRepository userRepository;
    private final CityRepository cityRepository;
    private final GameDataRepository gameDataRepository;
    private final GobernadorDataRepository gobernadorDataRepository;
    private final PlayerDataRepository playerDataRepository;
    private final CongresoRepository congresoRepository;

    @Override
    @Transactional
    public String createGame() {

        gameDataRepository.save(
                GameData.builder()
                        .turno(0)
                        .gameRegions(createRegions())
                        .build()
        );

        congresoRepository.save(
                Congreso.builder()
                        .plata(0)
                        .milicia(0)
                        .build()
        );

        return NEW_GAME_CREATED;
    }

    @Override
    public String addRoles(ManageRolesRequest request) {

        UserIndependencia user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_EMAIL));

        user.getRoles().add(request.getRole());
        PlayerData playerData = request.getRole().createPlayerData();
        playerData.setUsername(user.getUsername());
        playerData.setRol(request.getRole());

        setRevolucionarioData(playerData);

        GameData gameData = gameDataRepository.findFirstByOrderById()
                .orElseThrow(() -> new GameDataNotFoundException());

        playerData.setGameData(gameData);

        PlayerData playerSaved = playerDataRepository.save(playerData);
        user.setPlayerDataId(playerSaved.getId());
        userRepository.save(user);

        return ROLES_ACTUALIZADOS_CON_ÉXITO;
    }

    @Override
    public String removeRoles(ManageRolesRequest request) {

        UserIndependencia user = userRepository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_BY_EMAIL));

        user.getRoles().removeIf(userRole -> userRole == request.getRole());
        user.setPlayerDataId(null);

        userRepository.save(user);

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

        Set<Card> cards = new HashSet<>();
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

    private Set<GameRegion> createRegions(){
        Set<GameRegion> gameRegions = new HashSet<>();
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
                                                        .build();
                                            })
                                            .collect(Collectors.toSet())
                            )
                            .build());
                }
        );
        return gameRegions;
    }

    private City createCityFromCityEnum(CiudadInitEnum cityEnum) {
        return City.builder()
                .name(cityEnum.getNombre())
                .buildings(new HashSet<>(cityEnum.getEdificios()))
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
}
