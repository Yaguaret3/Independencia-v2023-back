package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class PoorlyCreatedGame extends CustomRuntimeException {

    public PoorlyCreatedGame(){
        super("Juego mal creado");
    }
}
