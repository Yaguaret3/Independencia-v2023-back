package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.data.GameData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GameDataTinyResponse {

    private Long id;
    private List<PlayerDataTinyResponse> players;
    private List<GameRegionTinyResponse> gameRegions;
    private Integer turno;
    private Long nextEndOfTurn;
    private String fase;

    public static GameDataTinyResponse toTinyResponse(GameData entity){
        return GameDataTinyResponse.builder()
                .id(entity.getId())
                .players(entity.getPlayers()
                        .stream()
                        .map(PlayerDataTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .gameRegions(entity.getGameRegions()
                        .stream()
                        .map(GameRegionTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .turno(entity.getTurno())
                .nextEndOfTurn(entity.getNextEndOfTurn())
                .fase(entity.getFase())
                .build();
    }
}
