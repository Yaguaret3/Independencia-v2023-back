package com.megajuegos.independencia.exceptions;

public class VotationNotFoundException extends GenericNotFoundException{
    public VotationNotFoundException(Long id) {
        super(id, "una votación");
    }
    public VotationNotFoundException() {
        super(0L, "ninguna votación activa");
    }
}
