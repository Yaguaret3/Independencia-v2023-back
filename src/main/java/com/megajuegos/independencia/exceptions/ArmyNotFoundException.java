package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class ArmyNotFoundException extends GenericNotFoundException {

    public ArmyNotFoundException(Long id){
        super(id, "un ejército");
    }
}
