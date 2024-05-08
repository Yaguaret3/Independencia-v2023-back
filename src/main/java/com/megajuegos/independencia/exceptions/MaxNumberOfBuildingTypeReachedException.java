package com.megajuegos.independencia.exceptions;

public class MaxNumberOfBuildingTypeReachedException extends RuntimeException{

    public MaxNumberOfBuildingTypeReachedException(String type){
        super((String.format("Número máximo de edificios de tipo %s alcanzado", type)));
    }
}
