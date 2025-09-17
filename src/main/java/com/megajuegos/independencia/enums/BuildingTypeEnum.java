package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
public enum BuildingTypeEnum {

    CUARTEL(1, "Reduce en 1 el precio de plata de las milicias"){
        @Override
        public void action() {

        }
    },
    ADMINISTRACION(2, "Sin efecto"){ // Corrupción disminuida
        @Override
        public void action() {

        }
    },
    IGLESIA(3, "Facilidad para generar intrigas. Buscá a Control de Trama"){
        @Override
        public void action() {

        }
    },
    ACUEDUCTO(4, "+1 voto por población"){
        @Override
        public void action() {

        }
    },
    ESCUELA(5, "Negociador. Consumible. Dáselo a un caudillo. Gana una batalla sin que nadie pierda unidades."){
        @Override
        public void action() {

        }
    },
    HOSPITAL(6, "Cirujano. Consumible. Dáselo a un caudillo. Reduce las bajas de milicias involucradas a la mitad."){
        @Override
        public void action() {

        }
    },
    MONUMENTO(7, "+1 prestigio"){
        @Override
        public void action() {

        }
    },
    CIRCO(8, "+2 a la opinión pública"){
        @Override
        public void action() {

        }
    },
    RECAUDADOR(9, "+1 a la recaudación de impuestos"){
        @Override
        public void action() {

        }
    },
    MURO(10, "Impide un ataque en la subregión de la ciudad. Único uso."){
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
