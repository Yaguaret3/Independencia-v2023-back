package com.megajuegos.independencia.exceptions;

public class PoorlyCreatedGame extends RuntimeException{

    public PoorlyCreatedGame(){
        super("Juego mal creado");
    }
}
