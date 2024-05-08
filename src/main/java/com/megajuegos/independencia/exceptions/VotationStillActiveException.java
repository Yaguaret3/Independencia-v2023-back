package com.megajuegos.independencia.exceptions;

public class VotationStillActiveException extends RuntimeException{

    public VotationStillActiveException(){
        super("Hay una votación todavía activa");
    }
}
