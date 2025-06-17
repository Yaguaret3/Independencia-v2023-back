package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class VotationNotFoundException extends GenericNotFoundException {
    public VotationNotFoundException(Long id) {
        super(id, "una votación");
    }
    public VotationNotFoundException() {
        super(0L, "ninguna votación activa");
    }
}
