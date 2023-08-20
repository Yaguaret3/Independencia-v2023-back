package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.tiny.BattleCardTinyResponse;
import com.megajuegos.independencia.dto.response.tiny.DisciplineSpentTinyResponse;
import com.megajuegos.independencia.entities.Battle;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class BattleFullResponse {

    private Long id;
    private List<BattleCardTinyResponse> cartasDeCombate;
    private List<ArmyResponse> ejercitos;
    private GameSubRegionFullResponse gameRegion;
    private Integer turnoDeJuego;
    private List<DisciplineSpentTinyResponse> disciplinaUsada;
    private Boolean active;

    public static BattleFullResponse toFullResponse(Battle entity){

        return BattleFullResponse.builder()
                .id(entity.getId())
                .cartasDeCombate(entity.getCartasDeCombate()
                        .stream()
                        .map(BattleCardTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .ejercitos(entity.getEjercitos()
                        .stream()
                        .map(ArmyResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .gameRegion(GameSubRegionFullResponse.toFullResponse(entity.getGameSubRegion()))
                .turnoDeJuego(entity.getTurnoDeJuego())
                .disciplinaUsada(entity.getDisciplinaUsada()
                        .stream()
                        .map(DisciplineSpentTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .active(entity.getActive())
                .build();
    }
}
