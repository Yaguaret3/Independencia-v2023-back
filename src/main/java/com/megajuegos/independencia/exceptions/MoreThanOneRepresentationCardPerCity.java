package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class MoreThanOneRepresentationCardPerCity extends CustomRuntimeException {

    public MoreThanOneRepresentationCardPerCity(String city){
        super(String.format("Hay más de una carta de representación para la ciudad %s", city));
    }
}
