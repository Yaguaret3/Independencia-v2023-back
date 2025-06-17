package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.enums.ActionTypeEnum;
import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class IncorrectActionTypeException extends CustomRuntimeException {

    public IncorrectActionTypeException(ActionTypeEnum type){

        super(String.format("En este momento es incorrecto jugar acciones de tipo: %s", type.getNombre()));

    }
}
