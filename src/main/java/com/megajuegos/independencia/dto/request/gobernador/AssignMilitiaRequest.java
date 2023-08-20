package com.megajuegos.independencia.dto.request.gobernador;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AssignMilitiaRequest {

    private Long idJugadorDestino;
    private Integer cantidadMilicias;
}
