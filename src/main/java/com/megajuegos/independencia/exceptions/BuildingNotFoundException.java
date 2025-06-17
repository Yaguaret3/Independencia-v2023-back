package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class BuildingNotFoundException extends GenericNotFoundException {

    public BuildingNotFoundException(Long id){
        super(id, "un edificio");
    }
}
