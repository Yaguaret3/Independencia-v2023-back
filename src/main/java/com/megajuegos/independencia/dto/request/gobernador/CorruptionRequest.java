package com.megajuegos.independencia.dto.request.gobernador;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CorruptionRequest {

    private Boolean aumentar;
    private Boolean disminuir;
    private Integer cantidad;
}
