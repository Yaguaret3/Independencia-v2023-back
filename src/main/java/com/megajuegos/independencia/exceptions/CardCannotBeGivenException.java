package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class CardCannotBeGivenException extends CustomRuntimeException {

    public CardCannotBeGivenException(Long id, String destinatarioRol, String destinatario){
        super(String.format("La carta con id: %s no puede ser dada a %s %s", id, destinatarioRol, destinatario));
    }
}
