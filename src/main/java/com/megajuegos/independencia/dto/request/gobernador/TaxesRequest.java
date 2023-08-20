package com.megajuegos.independencia.dto.request.gobernador;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaxesRequest {

    private boolean aumentar;
    private boolean disminuir;
}
