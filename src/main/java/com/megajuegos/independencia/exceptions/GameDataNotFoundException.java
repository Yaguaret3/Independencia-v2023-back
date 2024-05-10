package com.megajuegos.independencia.exceptions;

public class GameDataNotFoundException extends RuntimeException{
    public GameDataNotFoundException() {
        super("No hay juegos activos");
    }
    public GameDataNotFoundException(Long id) {
        super(String.format("No hay juegos con id %s", id));
    }
}
