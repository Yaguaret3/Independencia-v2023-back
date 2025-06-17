package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class RouteNotFoundException extends GenericNotFoundException {
    public RouteNotFoundException(Long id) {
        super(id, "una ruta");
    }
}
