package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
public enum BattleTypeEnum {

    CARGA_DE_INFANTERIA(1);

    private Integer id;

    BattleTypeEnum(Integer id){
        this.id = id;
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
