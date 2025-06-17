package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class InsufficientMilitiaException extends CustomRuntimeException {

    public InsufficientMilitiaException(Integer presente){
        super(String.format("Milicia insuficiente. A disposición: %s", presente));
    }
}
