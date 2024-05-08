package com.megajuegos.independencia.exceptions;

public class ArmyNotFoundException extends GenericNotFoundException{

    public ArmyNotFoundException(Long id){
        super(id, "un ejército");
    }
}
