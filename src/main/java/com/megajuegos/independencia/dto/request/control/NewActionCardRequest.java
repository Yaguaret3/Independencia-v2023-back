package com.megajuegos.independencia.dto.request.control;

import com.megajuegos.independencia.enums.ActionTypeEnum;
import lombok.*;

@Getter
@Setter
public class NewActionCardRequest {

    private ActionTypeEnum actionType;
}
