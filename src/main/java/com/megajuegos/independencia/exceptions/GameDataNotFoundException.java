package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class GameDataNotFoundException extends CustomRuntimeException {
    public GameDataNotFoundException() {
        super("No hay juegos activos");
    }
    public GameDataNotFoundException(Long id) {
        super(String.format("No hay juegos con id %s", id));
    }
}
