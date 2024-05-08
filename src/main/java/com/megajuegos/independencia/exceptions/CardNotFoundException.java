package com.megajuegos.independencia.exceptions;

public class CardNotFoundException extends GenericNotFoundException{
    public CardNotFoundException(Long id) {
        super(id, "una carta");
    }
}
