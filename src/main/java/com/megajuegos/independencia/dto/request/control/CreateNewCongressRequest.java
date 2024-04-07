package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateNewCongressRequest {

    private Long presidenteId;
    private Integer plata;
    private Integer milicia;
    private List<Long> diputadosIds;
    private Long sedeId;
}
