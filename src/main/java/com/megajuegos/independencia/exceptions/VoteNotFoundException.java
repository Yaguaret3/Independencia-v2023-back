package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class VoteNotFoundException extends GenericNotFoundException {
    public VoteNotFoundException(Long id) {
        super(id, "un voto");
    }
}
