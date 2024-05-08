package com.megajuegos.independencia.exceptions;

public class InsufficientMilitiaException extends RuntimeException{

    public InsufficientMilitiaException(Integer presente){
        super(String.format("Milicia insuficiente. A disposición: %s", presente));
    }
}
