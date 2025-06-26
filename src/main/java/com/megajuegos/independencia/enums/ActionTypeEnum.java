package com.megajuegos.independencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ActionTypeEnum {

    MOVIMIENTO(1, "Movimiento", "Movés tu campamento a otra región adyacente a una subregión que ocupes", 0, ""),
    ATAQUE(2, "Ataque", "Atacás a un ejército enemigo en una subregión que ocupes", 0, ""),
    DEFENSA(3, "Defensa", "Defendés un ataque en una región", 0, ""),
    REACCION(4, "Reaccion", "Interceptás a un ejército enemigo que mueve su campamento a otra región e iniciás una batalla", 0, ""),
    DESPLIEGUE(5, "Despliegue", "Ocupás con un ejército una subregión adyacente a una que ya ocupes o a tu campamento", 0, ""),
    ACAMPE(6, "Acampar", "Movés tu campamento a otra subregión de esta región", 0, "");
    //NADA(7, "Nada", "0", 0, "");

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer bonificador;
    private String condicion;

    public static ActionTypeEnum fromName(String name) throws InstanceNotFoundException {
        for(ActionTypeEnum e : ActionTypeEnum.values()){
            if(Objects.equals(e.name(), name)){
                return e;
            }
        }
        throw new InstanceNotFoundException("No existe una acción con esa id");
    }

}
