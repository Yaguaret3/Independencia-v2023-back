package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class WrongRoleException extends CustomRuntimeException {

    public WrongRoleException(){
        super("El rol seleccionado es incorrecto");
    }
}
