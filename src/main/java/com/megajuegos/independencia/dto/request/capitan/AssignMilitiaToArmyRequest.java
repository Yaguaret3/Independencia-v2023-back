package com.megajuegos.independencia.dto.request.capitan;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class AssignMilitiaToArmyRequest {

    private Long battleId;
    private Integer milicia;
}
