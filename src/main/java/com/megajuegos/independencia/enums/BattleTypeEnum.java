package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
public enum BattleTypeEnum {

    //VALOR DE ATAQUE
    CARGA_DE_INFANTERIA(1, "Carga de infantería", "Suma una vez más el valor de las milicias involucradas al ejército"),
    FUEGO_DE_ARTILLERIA(2, "Fuego de artillería", "Suma una vez más el doble del valor de las milicias involucradas al ejército"),
    FLANQUEO_DE_CABALLERIA(3, "Flanqueo de caballería", "Reduce a la mitad el valor del ejército rival"),

    //ANULA EFECTO
    MOVIMIENTO_EN_PINZAS(4, "Movimiento en pinzas", "Anula el efecto de la anterior carta rival"),
    FALSA_RETIRADA(5, "Falsa retirada", "Anula el efecto de la siguiente carta rival"),

    //CAMBIOS AL FINAL
    REAGRUPAMIENTO(7, "Reagrupamiento", "Al finalizar la asignación de órdenes de batalla, descarta esta y dos cartas jugadas para descartar una carta rival jugada hasta aquí"),
    ADAPTACION(9, "Adaptación", "Al finalizar la asignación de órdenes de batalla, descarta esta carta y se juega otra en su lugar"),

    //DERROTA MENOR
    RETIRADA_ORDENADA(10, "Retirada ordenada", "Pierde inmediatamente, pero sólo la mitad de las milicias involucradas");


    private final Integer id;
    private final String nombre;
    private final String efecto;

    BattleTypeEnum(Integer id, String nombre, String efecto){
        this.id = id;
        this.nombre = nombre;
        this.efecto =efecto;
    }

    public static BattleTypeEnum fromName(String name) throws InstanceNotFoundException {

        for(BattleTypeEnum type : BattleTypeEnum.values()){
            if(Objects.equals(type.name(), name)){
                return type;
            }
        }
        throw new InstanceNotFoundException("No existe una orden de batalla con esa id");
    }
}
