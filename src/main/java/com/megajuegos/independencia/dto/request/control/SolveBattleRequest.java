package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SolveBattleRequest {

    private Long battleId;
    private List<ArmyPostBattleRequest> resultados;
}
