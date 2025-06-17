package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class CardNotFoundException extends GenericNotFoundException {
    public CardNotFoundException(Long id) {
        super(id, "una carta");
    }
}
