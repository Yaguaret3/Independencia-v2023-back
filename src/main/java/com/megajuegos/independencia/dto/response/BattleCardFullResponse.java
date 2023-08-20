package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.BattleCard;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BattleCardFullResponse {

    private Long id;
    private Boolean alreadyPlayed;
    private Integer turnWhenPlayed;
    private Long batallaId;
    private Long playerId;
    private String tipoOrdenDeBatalla;

    public static BattleCardFullResponse toFullResponse(BattleCard entity){

        return BattleCardFullResponse.builder()
                .id(entity.getId())
                .playerId(entity.getPlayerData().getId())
                .alreadyPlayed(entity.getAlreadyPlayed())
                .turnWhenPlayed(entity.getTurnWhenPlayed())
                .batallaId(entity.getBatalla().getId())
                .tipoOrdenDeBatalla(entity.getTipoOrdenDeBatalla().name())
                .build();
    }

}
