package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CreateBattleRequest {

    private List<ArmyForBattleRequest> combatientes;
    private Long gameSubRegionId;
}
