package com.megajuegos.independencia.dto.request.control;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewArmyRequest {

    private Long capitanId;
    private Long gameSubRegionId;
    private Integer milicia;
}
