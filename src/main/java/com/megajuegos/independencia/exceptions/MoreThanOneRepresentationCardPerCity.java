package com.megajuegos.independencia.exceptions;

public class MoreThanOneRepresentationCardPerCity extends RuntimeException{

    public MoreThanOneRepresentationCardPerCity(String city){
        super(String.format("Hay más de una carta de representación para la ciudad %s", city));
    }
}
