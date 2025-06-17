package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class CongresoNotFoundException extends GenericNotFoundException {
    public CongresoNotFoundException(Long id) {
        super(id, "un congreso");
    }
}
