package com.megajuegos.independencia.exceptions.common;

public class GenericNotFoundException extends RuntimeException{

    public GenericNotFoundException(Long id, String element){
        super(String.format("No se encuentra %s con id: %s", element, id));
    }
}
