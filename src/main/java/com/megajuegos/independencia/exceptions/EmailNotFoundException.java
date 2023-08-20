package com.megajuegos.independencia.exceptions;

public class EmailNotFoundException extends RuntimeException{

    private static final String NOT_FOUND = "No existe ningún usuario registrado con el email + '%s'";

    public EmailNotFoundException(String s){
        super(String.format(NOT_FOUND, s));
    }
}
