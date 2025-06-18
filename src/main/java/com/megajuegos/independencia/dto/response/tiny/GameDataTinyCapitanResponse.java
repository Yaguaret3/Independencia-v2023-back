package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.dto.response.BattleFullResponse;
import com.megajuegos.independencia.entities.Battle;
import com.megajuegos.independencia.entities.GameRegion;
import com.megajuegos.independencia.entities.GameSubRegion;
import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.entities.data.GameData;
import com.megajuegos.independencia.enums.PhaseEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GameDataTinyCapitanResponse {

    private Long id;
    private List<PlayerDataTinyResponse> players;
    private GameRegionTinyResponse gameRegion;
    private List<BattleFullResponse> ownBattles;
    private List<GameRegionVeryTinyResponse> gameRegionsTiny;
    private Integer turno;
    private Long nextEndOfTurn;
    private PhaseEnum fase;

    public static GameDataTinyCapitanResponse toDtoResponse(CapitanData capitanData){

        GameData gameData = capitanData.getGameData();
        GameRegion gameRegion = capitanData.getCamp().getGameRegion();

        return GameDataTinyCapitanResponse.builder()
                .id(gameData.getId())
                .players(gameData.getPlayers().stream()
                        .map(PlayerDataTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .gameRegion(GameRegionTinyResponse.toTinyResponse(gameRegion))
                .ownBattles(gameRegion.getSubRegions().stream()
                                .flatMap(sr -> sr.getBatallas().stream())
                                .filter(Battle::getActive)
                                .filter(b -> (
                                        b.getCombatientes().stream()
                                            .anyMatch(e -> capitanData.equals(e.getCapitanData()))))
                                .map(BattleFullResponse::toFullResponse)
                                .collect(Collectors.toList()))

                .turno(gameData.getTurno())
                .nextEndOfTurn(gameData.getNextEndOfTurn())
                .fase(gameData.getFase())
                .gameRegionsTiny(gameData.getGameRegions().stream()
                        .map(GameRegionVeryTinyResponse::toVeryTinyDto)
                        .collect(Collectors.toList()))
                .build();
    }

}
