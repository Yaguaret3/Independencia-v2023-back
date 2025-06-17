package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class IncorrectPhaseException extends CustomRuntimeException {

    public IncorrectPhaseException(){
        super("La acción es incorrecta para la fase actual");
    }
}
