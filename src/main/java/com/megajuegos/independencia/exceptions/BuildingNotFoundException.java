package com.megajuegos.independencia.exceptions;

public class BuildingNotFoundException extends GenericNotFoundException{

    public BuildingNotFoundException(Long id){
        super(id, "un edificio");
    }
}
