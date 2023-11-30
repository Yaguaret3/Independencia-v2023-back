package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArmyPostBattleRequest {

    private Long armyId;
    private Integer miliciasPerdidas;
    private boolean destruido;
}
