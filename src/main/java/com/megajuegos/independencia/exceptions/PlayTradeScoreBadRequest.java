package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class PlayTradeScoreBadRequest extends CustomRuntimeException {
    public PlayTradeScoreBadRequest() {
        super("Por favor, armá una ruta entre dos o más ciudades");
    }
}
