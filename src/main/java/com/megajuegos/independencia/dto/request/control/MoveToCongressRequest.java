package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MoveToCongressRequest {

    private Long revolucionarioId;
    private Long congresoId;
}
