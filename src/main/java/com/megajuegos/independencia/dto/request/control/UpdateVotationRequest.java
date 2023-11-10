package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVotationRequest {

    private String propuesta;
    private Boolean active;
}
