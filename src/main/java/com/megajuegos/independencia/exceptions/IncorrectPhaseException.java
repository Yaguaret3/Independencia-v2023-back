package com.megajuegos.independencia.exceptions;

public class IncorrectPhaseException extends RuntimeException{

    public IncorrectPhaseException(){
        super("La acción es incorrecta para la fase actual");
    }
}
