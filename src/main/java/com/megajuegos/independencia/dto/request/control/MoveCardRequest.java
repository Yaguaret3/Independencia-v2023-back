package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveCardRequest {

    private Long fromId;
    private Long toId;
}
