package com.megajuegos.independencia.exceptions;

public class NotCongressPresidentException extends RuntimeException{

    public NotCongressPresidentException(String revolucionarioName){
        super(String.format("El diputado %s no es el presidente del congreso", revolucionarioName));
    }
}
