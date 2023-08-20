package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
public enum BuildingTypeEnum {

    CUARTEL(1, "Unidades más baratas"){
        @Override
        public void action() {

        }
    },
    ADMINISTRACION(2, "Menor/Baja corrupción"){
        @Override
        public void action() {

        }
    },
    IGLESIA(3, "Bonificador a la tirada de espias"){
        @Override
        public void action() {

        }
    },
    ACUEDUCTO(4, "+1 voto por población"){
        @Override
        public void action() {

        }
    },
    ESCUELA(5, "Carta de oficial"){
        @Override
        public void action() {

        }
    },
    HOSPITAL(6, "Carta de hospitales de campaña"){
        @Override
        public void action() {

        }
    },
    MONUMENTO(7, "+1 prestigio"){
        @Override
        public void action() {

        }
    },
    CIRCO(8, "+ opinión pública"){
        @Override
        public void action() {

        }
    },
    SECRETARIA_DE_FINANZAS(9, "+ impuestos"){
        @Override
        public void action() {

        }
    };

    private Integer id;
    private String bonificacion;
    private Integer max;

    BuildingTypeEnum(Integer id, String bonificacion){
        this.id = id;
        this.bonificacion = bonificacion;
        this.max = 2;
    }

    public static BuildingTypeEnum fromId(Integer id) throws InstanceNotFoundException {
        for(BuildingTypeEnum e : BuildingTypeEnum.values()){
            if(Objects.equals(e.getId(), id)){
                return e;
            }
        }
        throw new InstanceNotFoundException("No existe un edificio con esa id");
    }

    public abstract void action();

}
