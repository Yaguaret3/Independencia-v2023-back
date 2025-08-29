package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class PlayerNotFoundException extends GenericNotFoundException {
    public PlayerNotFoundException(Long id) {
        super(id, "un jugador");
    }
    public PlayerNotFoundException() {
        super(0L, "el jugador logueado");
    }

    public PlayerNotFoundException(String message) {
        super(message);
    }
}
