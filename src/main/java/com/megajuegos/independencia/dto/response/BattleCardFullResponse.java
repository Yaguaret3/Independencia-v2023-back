package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.BattleCard;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BattleCardFullResponse {

    private Long id;
    private Boolean alreadyPlayed;
    private Integer turnWhenPlayed;
    private Long playerId;
    private String battleOrderType;

    public static BattleCardFullResponse toFullResponse(BattleCard entity){

        return BattleCardFullResponse.builder()
                .id(entity.getId())
                .playerId(entity.getPlayerData().getId())
                .alreadyPlayed(entity.isAlreadyPlayed())
                .turnWhenPlayed(entity.getTurnWhenPlayed())
                .battleOrderType(entity.getTipoOrdenDeBatalla().name())
                .build();
    }

}
