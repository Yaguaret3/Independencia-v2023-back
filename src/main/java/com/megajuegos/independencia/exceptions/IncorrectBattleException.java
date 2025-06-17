package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class IncorrectBattleException extends CustomRuntimeException {

    public IncorrectBattleException(){
        super("Error en la creación de la batalla o la batalla no se encuentra");
    }
}
