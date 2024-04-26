package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
public enum BattleTypeEnum {

    CARGA_DE_INFANTERIA(1, "Carga de infantería");

    private Integer id;
    private String nombre;

    BattleTypeEnum(Integer id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public static BattleTypeEnum fromId(Integer id) throws InstanceNotFoundException {

        for(BattleTypeEnum type : BattleTypeEnum.values()){
            if(Objects.equals(type.getId(), id)){
                return type;
            }
        }
        throw new InstanceNotFoundException("No existe una orden de batalla con esa id");
    }
}
