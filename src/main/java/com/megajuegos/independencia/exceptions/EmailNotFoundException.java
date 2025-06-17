package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class EmailNotFoundException extends GenericNotFoundException {

    public EmailNotFoundException(String email){
        super(0L, String.format("el email %s", email));
    }
}
