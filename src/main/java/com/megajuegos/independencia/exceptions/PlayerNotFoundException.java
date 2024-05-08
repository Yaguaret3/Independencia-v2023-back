package com.megajuegos.independencia.exceptions;

public class PlayerNotFoundException extends GenericNotFoundException {
    public PlayerNotFoundException(Long id) {
        super(id, "un jugador");
    }
    public PlayerNotFoundException() {
        super(0L, "el jugador logueado");
    }
}
