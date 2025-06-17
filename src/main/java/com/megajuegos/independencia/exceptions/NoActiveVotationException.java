package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class NoActiveVotationException extends CustomRuntimeException {

    public NoActiveVotationException(){
        super("No hay ninguna votación activa");
    }
}
