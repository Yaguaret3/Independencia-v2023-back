package com.megajuegos.independencia.exceptions;

public class RouteNotFoundException extends GenericNotFoundException{
    public RouteNotFoundException(Long id) {
        super(id, "una ruta");
    }
}
