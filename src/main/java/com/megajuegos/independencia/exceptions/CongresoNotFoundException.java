package com.megajuegos.independencia.exceptions;

public class CongresoNotFoundException extends GenericNotFoundException{
    public CongresoNotFoundException(Long id) {
        super(id, "un congreso");
    }
}
