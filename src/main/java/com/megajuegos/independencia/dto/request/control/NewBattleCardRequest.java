package com.megajuegos.independencia.dto.request.control;

import com.megajuegos.independencia.enums.BattleTypeEnum;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewBattleCardRequest {

    @NotNull
    private BattleTypeEnum battleType;
}
