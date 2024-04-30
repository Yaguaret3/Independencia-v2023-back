package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.data.*;
import com.megajuegos.independencia.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PlayerDataTinyResponse {

    private Long playerId;
    private String playerName;
    private Long gameId;
    private Integer cards;
    private String rol;
    private boolean presidente;

    public static PlayerDataTinyResponse toTinyResponse(PlayerData entity){

        PlayerDataTinyResponse response = PlayerDataTinyResponse.builder()
                .playerId(entity.getId())
                .playerName(entity.getUser().getUsername())
                .gameId(entity.getGameData().getId())
                .cards(entity.getCards().size())
                .rol(entity.getRol().name())
                .build();

        if(entity instanceof RevolucionarioData){
            RevolucionarioData revData = (RevolucionarioData) entity;
            response.setPresidente(revData.isPresidente());
        }
        return response;
    }
}
