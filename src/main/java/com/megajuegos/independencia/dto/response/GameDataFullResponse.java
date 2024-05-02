package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.data.GameData;
import com.megajuegos.independencia.entities.data.MercaderData;
import com.megajuegos.independencia.enums.BuildingTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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

    public static GameDataFullResponse toFullResponse(GameData entity){

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
                .rutas(entity.getPlayers().stream()
                        .filter(MercaderData.class::isInstance)
                        .map(p -> (MercaderData) p)
                        .flatMap(m -> m.getRoutes().stream())
                        .filter(r-> r.getTurn().equals(entity.getTurno()))
                        .map(RouteResponse::toDto)
                        .collect(Collectors.toList()))

                .build();
    }
}
