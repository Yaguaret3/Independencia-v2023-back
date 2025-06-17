package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class BattleNotFoundException extends GenericNotFoundException {

    public BattleNotFoundException(Long id){
        super(id, "una batalla");
    }
}
