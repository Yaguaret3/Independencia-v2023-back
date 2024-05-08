package com.megajuegos.independencia.exceptions;

public class IncorrectBattleException extends RuntimeException{

    public IncorrectBattleException(){
        super("Error en la creación de la batalla o la batalla no se encuentra");
    }
}
