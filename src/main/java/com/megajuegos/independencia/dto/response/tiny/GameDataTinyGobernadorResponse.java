package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.data.GameData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GameDataTinyGobernadorResponse {

    private Long id;
    private List<PlayerDataTinyResponse> players;
    private Integer turno;
    private Long nextEndOfTurn;
    private String fase;

    public static GameDataTinyGobernadorResponse toDtoResponse(GameData entity){

        return GameDataTinyGobernadorResponse.builder()
                .id(entity.getId())
                .players(entity.getPlayers().stream()
                        .map(PlayerDataTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .turno(entity.getTurno())
                .nextEndOfTurn(entity.getNextEndOfTurn())
                .fase(entity.getFase().getNombre())
                .build();
    }
}
