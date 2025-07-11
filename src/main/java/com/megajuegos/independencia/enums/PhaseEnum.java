package com.megajuegos.independencia.enums;

import lombok.Getter;

@Getter
public enum PhaseEnum {

    MOVING("Movimiento"),
    PLANNING("Planificación"),
    REVEALING("Revelación");

    private String nombre;

    PhaseEnum(String nombre) {
        this.nombre = nombre;
    }
}
