package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class IncorrectCardTypeException extends CustomRuntimeException {

    public IncorrectCardTypeException(){
        super("Se ha jugado una carta de tipo inválido");
    }
}
