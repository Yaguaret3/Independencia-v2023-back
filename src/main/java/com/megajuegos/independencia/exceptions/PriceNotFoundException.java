package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class PriceNotFoundException extends CustomRuntimeException {
    public PriceNotFoundException() {
        super("No se puede acceder al precio del objeto solicitado");
    }
}
