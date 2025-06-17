package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class EmailAlreadyExistsException extends CustomRuntimeException {

    private static final String message = "El correo '%s' ya está asignado a un usuario.";

    public EmailAlreadyExistsException(String email) {
        super(String.format(message, email));
    }
}
