package com.megajuegos.independencia.exceptions;

public class BattleNotFoundException extends GenericNotFoundException{

    public BattleNotFoundException(Long id){
        super(id, "una batalla");
    }
}
