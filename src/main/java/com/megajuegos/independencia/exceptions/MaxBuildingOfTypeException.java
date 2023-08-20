package com.megajuegos.independencia.exceptions;

public class MaxBuildingOfTypeException extends RuntimeException{

    private static final String ERR_MAX_BUILDINGS_CREATED = "No se puede construir otro edificio de tipo: ";

    public MaxBuildingOfTypeException(String s){
        super(String.format(ERR_MAX_BUILDINGS_CREATED, s));
    }
}
