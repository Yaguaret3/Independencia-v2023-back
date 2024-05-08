package com.megajuegos.independencia.exceptions;

public class CardCannotBeGivenException extends RuntimeException{

    public CardCannotBeGivenException(Long id, String destinatarioRol, String destinatario){
        super(String.format("La carta con id: %s no puede ser dada a %s %s", id, destinatarioRol, destinatario));
    }
}
