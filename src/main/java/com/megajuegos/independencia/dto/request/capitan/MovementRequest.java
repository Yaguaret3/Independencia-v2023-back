package com.megajuegos.independencia.dto.request.capitan;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovementRequest {

    private Long cardId;
    private Long regionToId;
}
