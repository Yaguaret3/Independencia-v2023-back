package com.megajuegos.independencia.dto.request.gobernador;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RepresentationRequest {

    Long idJugadorDestino;
    Long idRepresentationCard;
}
