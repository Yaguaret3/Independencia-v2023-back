package com.megajuegos.independencia.service.util;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestUtil {

    private Integer plata;
    private List<Long> resourcesIds;
    private Integer puntajeComercial;
    private Integer disciplina;
}
