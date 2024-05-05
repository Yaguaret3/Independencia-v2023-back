package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Army;
import com.megajuegos.independencia.entities.Battle;
import com.megajuegos.independencia.entities.Route;
import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.entities.data.GameData;
import com.megajuegos.independencia.entities.data.MercaderData;
import com.megajuegos.independencia.enums.BuildingTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data @Builder
public class GameDataFullResponse {

    private Long id;
    private List<PlayerDataFullResponse> playersData;
    private List<GameRegionFullResponse> gameRegions;
    private Integer turno;
    private Long nextEndOfTurn;
    private String fase;
    private List<BuildingResponse> edificios;
    private List<CongresoResponse> congresos;
    private List<RouteResponse> rutas;
    private List<BattleFullResponse> batallas;

    public static GameDataFullResponse toFullResponse(GameData entity, List<Route> rutas, List<Battle> batallas){

        return GameDataFullResponse.builder()
                .id(entity.getId())
                .playersData(entity.getPlayers()
                        .stream()
                        .map(PlayerDataFullResponse::toFullResponse)
                        .sorted(Comparator.comparing(PlayerDataFullResponse::getId))
                        .collect(Collectors.toList()))
                .gameRegions(entity.getGameRegions()
                        .stream()
                        .map(GameRegionFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .turno(entity.getTurno())
                .nextEndOfTurn(entity.getNextEndOfTurn())
                .fase(entity.getFase().name())
                .edificios(Arrays.stream(BuildingTypeEnum.values())
                        .map(b -> BuildingResponse.builder()
                                .id(b.getId().longValue())
                                .buildingType(b.name())
                                .bonification(b.getBonificacion())
                                .build())
                        .collect(Collectors.toList()))
                .congresos(entity.getCongresos().stream()
                        .map(CongresoResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .rutas(rutas.stream()
                        .map(RouteResponse::toDto)
                        .collect(Collectors.toList()))
                .batallas(batallas.stream()
                        .map(BattleFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
