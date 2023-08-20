package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.card.BattleCard;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BattleCardTinyResponse {

    private Long cardId;
    private Long battleId;
    private String battleOrderType;

    public static BattleCardTinyResponse toTinyResponse(BattleCard entity){
        return BattleCardTinyResponse.builder()
                .cardId(entity.getId())
                .battleId(entity.getBatalla().getId())
                .battleOrderType(entity.getTipoOrdenDeBatalla().name())
                .build();
    }
}
