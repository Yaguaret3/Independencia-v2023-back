package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.tiny.BattleCardTinyResponse;
import com.megajuegos.independencia.entities.Battle;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class BattleFullResponse {

    private Long id;
    private List<BattleCardFullResponse> cartasDeCombate;
    private List<ArmyResponse> otrosEjercitos;
    private ArmyResponse ejercitoAtaque;
    private ArmyResponse ejercitoDefensa;
    private GameSubRegionFullResponse gameRegion;
    private Integer ataque;
    private Integer defensa;
    private Integer turnoDeJuego;
    private Boolean active;

    public static BattleFullResponse toFullResponse(Battle entity){

        return BattleFullResponse.builder()
                .id(entity.getId())
                .cartasDeCombate(entity.getCartasDeCombate()
                        .stream()
                        .map(BattleCardFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .ejercitoAtaque(ArmyResponse.toDtoResponse(entity.getEjercitoAtaque()))
                .ejercitoDefensa(ArmyResponse.toDtoResponse(entity.getEjercitoDefensa()))
                .otrosEjercitos(entity.getOtrosEjercitos()
                        .stream()
                        .map(ArmyResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .ataque(entity.getAtaque())
                .defensa(entity.getDefensa())
                .gameRegion(GameSubRegionFullResponse.toFullResponse(entity.getGameSubRegion()))
                .turnoDeJuego(entity.getTurnoDeJuego())
                .active(entity.getActive())
                .build();
    }
}
