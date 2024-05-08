package com.megajuegos.independencia.exceptions;

public class GameDataNotFoundException extends RuntimeException{
    public GameDataNotFoundException() {
        super("No hay juegos activos");
    }
}
