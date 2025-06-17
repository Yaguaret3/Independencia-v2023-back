package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class CredentialsException extends CustomRuntimeException {

    public CredentialsException(String i){
        super(i);
    }
}
