package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.enums.ActionTypeEnum;

public class IncorrectActionTypeException extends RuntimeException{

    public IncorrectActionTypeException(ActionTypeEnum type){

        super(String.format("En este momento es incorrecto jugar acciones de tipo: %s", type.getNombre()));

    }
}
