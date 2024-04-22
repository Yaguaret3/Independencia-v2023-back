package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Army;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class ArmyFullResponse {

    private Long id;
    private Long capitanId;
    private String capitanName;
    private Long gameSubRegion;
    private String gameSubRegionName;
    private List<BattleCardFullResponse> cartasJugadas;
    private Integer milicias;
    private boolean ataque;
    private Integer valorAzar;
    private Integer valorProvisorio;
    public static ArmyFullResponse toDtoResponse(Army entity){

        return ArmyFullResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanData().getId())
                .capitanName(entity.getCapitanData().getUser().getUsername())
                .gameSubRegion(entity.getSubregion().getId())
                .gameSubRegionName(entity.getSubregion().getNombre())
                .cartasJugadas(entity.getCartasJugadas().stream()
                        .map(BattleCardFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .milicias(entity.getMilicias())
                .ataque(entity.isAtaque())
                .valorAzar(entity.getValorAzar())
                .valorProvisorio(entity.getValorProvisorio())
                .build();
    }

}
