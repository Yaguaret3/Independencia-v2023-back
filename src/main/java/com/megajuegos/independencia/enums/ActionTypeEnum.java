package com.megajuegos.independencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ActionTypeEnum {

    MOVIMIENTO(1, "Movimiento", "", 0, ""),
    ATAQUE(2, "Ataque", "", 0, ""),
    DEFENSA(3, "Defensa", "", 0, ""),
    REACCION(4, "Reaccion", "", 0, ""),
    DESPLIEGUE(5, "Despliegue", "", 0, ""),
    ACAMPE(6, "Acampar", "", 0, "");

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer bonificador;
    private String condicion;

    public static ActionTypeEnum fromId(Integer id) throws InstanceNotFoundException {
        for (ActionTypeEnum e : ActionTypeEnum.values()){
            if(Objects.equals(e.getId(), id)){
                return e;
            }
        }
        throw new InstanceNotFoundException("No existe una acción con esa id");
    }

}
