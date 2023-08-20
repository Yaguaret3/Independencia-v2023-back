package com.megajuegos.independencia.enums;

import com.megajuegos.independencia.entities.Building;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum CiudadInitEnum {

    BUENOS_AIRES("Buenos Aires", 0, 5, 5, 1,
            new ArrayList<>()),
    MONTEVIDEO("Montevideo", 0, 5, 5, 1,
            new ArrayList<>()),
    CORRIENTES("Corrientes", 0, 5, 5, 1,
            new ArrayList<>()),
    ASUNCION("Asunción", 0, 5, 5, 1,
            new ArrayList<>()),
    CORDOBA("Córdoba", 0, 5, 5, 1,
            new ArrayList<>()),
    MENDOZA("Mendoza", 0, 5, 5, 1,
            new ArrayList<>()),
    TUCUMAN("Tucumán", 0, 5, 5, 1,
            new ArrayList<>()),
    SALTA("Salta", 0, 5, 5, 1,
            new ArrayList<>()),
    POTOSI("Potosí", 0, 5, 5, 1,
            new ArrayList<>()),
    LA_PAZ("La Paz", 0, 5, 5, 1,
            new ArrayList<>());

    private final String nombre;
    private final Integer prestigio;
    private final Integer opinionPublica;
    private final Integer nivelImpositivo;
    private final Integer nivelMercado;
    private final List<Building> edificios;

    CiudadInitEnum(String nombre,
                   Integer prestigio,
                   Integer opinionPublica,
                   Integer nivelImpositivo,
                   Integer nivelMercado,
                   List<Building> edificios){

        this.nombre = nombre;
        this.prestigio = prestigio;
        this.opinionPublica = opinionPublica;
        this.nivelImpositivo = nivelImpositivo;
        this.nivelMercado = nivelMercado;
        this.edificios = edificios;
    }

}
