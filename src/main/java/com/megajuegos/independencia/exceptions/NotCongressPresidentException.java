package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class NotCongressPresidentException extends CustomRuntimeException {

    public NotCongressPresidentException(String revolucionarioName){
        super(String.format("El diputado %s no es el presidente del congreso", revolucionarioName));
    }
}
