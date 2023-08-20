package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;

@Getter
public enum RepresentationEnum {

    BUENOS_AIRES(1, "Buenos Aires", 0),
    MONTEVIDEO(2, "Montevideo", 0),
    CONCEPCION_DEL_URUGUAY(3, "Concepción del Uruguay", 0),
    CORRIENTES(4, "Corrientes", 0),
    ASUNCION(5, "Asunción",0),
    SANTA_FE(6, "Santa Fe", 0),
    CORDOBA(7, "Córdoba", 0),
    SAN_LUIS(8, "San Luis", 0),
    MENDOZA(9, "Mendoza", 0),
    SAN_JUAN(10, "San Juan", 0),
    LA_RIOJA(11, "La Rioja", 0),
    CATAMARCA(12, "Catamarca", 0),
    SANTIAGO_DEL_ESTERO(13, "Santiago del Estero", 0),
    TUCUMAN(14, "Tucumán", 0),
    SALTA(15, "Salta", 0),
    JUJUY(16, "Jujuy", 0),
    TARIJA(17, "Tarija", 0),
    POTOSI(18, "Potosí", 0),
    CHUQUISACA(19, "Chuquisaca", 0),
    COCHABAMBA(20, "Cochabamba", 0),
    LA_PAZ(21, "La Paz", 0),
    RIO_CUARTO(22, "Río Cuarto", 6),
    MISIONES(23, "Misiones", 14);

    private Integer id;
    private String nombre;
    private Integer poblacion;

    RepresentationEnum(Integer id, String nombre, Integer poblacion){
        this.id = id;
        this.nombre = nombre;
        this.poblacion = poblacion;
    }

    public static RepresentationEnum byNombre(String name) throws InstanceNotFoundException {
        for(RepresentationEnum e : values()){
            if(e.nombre.equals(name)){
                return e;
            }
        }
        throw new InstanceNotFoundException("No existe una ciudad con esa id");
    }


}
