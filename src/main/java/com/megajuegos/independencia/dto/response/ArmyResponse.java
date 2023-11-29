package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Army;
import com.megajuegos.independencia.entities.card.BattleCard;
import lombok.Builder;
import lombok.Data;

import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class ArmyResponse {

    private Long id;
    private Long capitanId;
    private String capitanName;
    private Long gameSubRegion;
    private List<BattleCardFullResponse> cartasJugadas;
    private boolean ataque;
    private Integer valorAzar;
    private Integer valorProvisorio;
    public static ArmyResponse toDtoResponse(Army entity){

        return ArmyResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanData().getId())
                .capitanName(entity.getCapitanData().getUsername())
                .gameSubRegion(entity.getGameSubRegion().getId())
                .cartasJugadas(entity.getCartasJugadas().stream()
                        .map(BattleCardFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .ataque(entity.isAtaque())
                .valorAzar(entity.getValorAzar())
                .valorProvisorio(entity.getValorProvisorio())
                .build();
    }

}
