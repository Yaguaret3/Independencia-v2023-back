package com.megajuegos.independencia.exceptions;

public class NoActiveVotationException extends RuntimeException{

    public NoActiveVotationException(){
        super("No hay ninguna votación activa");
    }
}
