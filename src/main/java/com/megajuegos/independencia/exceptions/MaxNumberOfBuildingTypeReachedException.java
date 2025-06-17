package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class MaxNumberOfBuildingTypeReachedException extends CustomRuntimeException {

    public MaxNumberOfBuildingTypeReachedException(String type){
        super((String.format("Número máximo de edificios de tipo %s alcanzado", type)));
    }
}
