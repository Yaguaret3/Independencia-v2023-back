package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class VoteAlreadySentException extends CustomRuntimeException {

    public VoteAlreadySentException(){
        super("Voto ya enviado");
    }
}
