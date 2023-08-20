package com.megajuegos.independencia.dto.request.mercader;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class GiveResourcesRequest {

    private List<Long> resourceIds;
    private Long idJugadorDestino;
}
