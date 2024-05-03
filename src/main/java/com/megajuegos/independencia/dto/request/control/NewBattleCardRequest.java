package com.megajuegos.independencia.dto.request.control;

import com.megajuegos.independencia.enums.BattleTypeEnum;
import lombok.*;

@Getter
@Setter
public class NewBattleCardRequest {

    private BattleTypeEnum battleType;
}
