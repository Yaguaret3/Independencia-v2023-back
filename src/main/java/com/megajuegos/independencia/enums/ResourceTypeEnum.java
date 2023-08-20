package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
public enum ResourceTypeEnum {

    TEXTIL(1),
    AGROPECUARIA(2),
    METALMECANICA(3),
    CONSTRUCCION(4),
    COMERCIAL(5);

    private Integer id;

    ResourceTypeEnum(Integer id) {
        this.id = id;
    }

    public static ResourceTypeEnum fromId(Integer id) throws InstanceNotFoundException {
        for(ResourceTypeEnum e : ResourceTypeEnum.values()){
            if(Objects.equals(e.getId(), id)){
                return e;
            }
        }
        throw new InstanceNotFoundException("No existe un recurso con esa id");
    }
}
