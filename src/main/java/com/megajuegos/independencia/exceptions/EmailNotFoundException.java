package com.megajuegos.independencia.exceptions;

public class EmailNotFoundException extends GenericNotFoundException{

    public EmailNotFoundException(String email){
        super(0L, String.format("el email %s", email));
    }
}
