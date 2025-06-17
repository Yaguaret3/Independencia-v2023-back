package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class SubRegionNotFoundException extends GenericNotFoundException {
    public SubRegionNotFoundException(Long id) {
        super(id, "una subregion");
    }
}
