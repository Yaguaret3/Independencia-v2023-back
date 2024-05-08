package com.megajuegos.independencia.exceptions;

public class VoteAlreadySentException extends RuntimeException{

    public VoteAlreadySentException(){
        super("Voto ya enviado");
    }
}
