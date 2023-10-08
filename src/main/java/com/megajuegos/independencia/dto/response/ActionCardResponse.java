package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.ActionCard;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ActionCardResponse {

    private Long id;
    private Long playerId;
    private Boolean alreadyPlayed;
    private Integer turnWhenPlayed;
    private String actionType;

    public static ActionCardResponse toDtoResponse(ActionCard entity){

        return ActionCardResponse.builder()
                .id(entity.getId())
                .playerId(entity.getPlayerData().getId())
                .alreadyPlayed(entity.getAlreadyPlayed())
                .turnWhenPlayed(entity.getTurnWhenPlayed())
                .actionType(entity.getTipoAccion().name())
                .build();
    }
}
