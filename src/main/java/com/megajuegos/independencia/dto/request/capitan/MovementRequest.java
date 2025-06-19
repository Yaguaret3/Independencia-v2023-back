package com.megajuegos.independencia.dto.request.capitan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementRequest {

    @NotNull(message = "Seleccionar una carta es obligatorio")
    private Long cardId;
    @NotNull(message = "Seleccionar una región es obligatorio")
    private Long regionToId;
}
