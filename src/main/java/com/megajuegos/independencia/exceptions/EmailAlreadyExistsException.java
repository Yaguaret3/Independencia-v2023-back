package com.megajuegos.independencia.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{

    private static final String message = "El correo '%s' ya está asignado a un usuario.";

    public EmailAlreadyExistsException(String email) {
        super(String.format(message, email));
    }
}
