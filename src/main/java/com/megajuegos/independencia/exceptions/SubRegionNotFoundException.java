package com.megajuegos.independencia.exceptions;

public class SubRegionNotFoundException extends GenericNotFoundException{
    public SubRegionNotFoundException(Long id) {
        super(id, "una subregion");
    }
}
