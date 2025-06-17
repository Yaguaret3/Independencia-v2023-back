package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class VotationStillActiveException extends CustomRuntimeException {

    public VotationStillActiveException(){
        super("Hay una votación todavía activa");
    }
}
