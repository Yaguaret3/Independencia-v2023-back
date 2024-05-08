package com.megajuegos.independencia.exceptions;

public class IncorrectCardTypeException extends RuntimeException{

    public IncorrectCardTypeException(){
        super("Se ha jugado una carta de tipo inválido");
    }
}
