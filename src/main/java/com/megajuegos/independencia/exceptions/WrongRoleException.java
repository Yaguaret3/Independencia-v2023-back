package com.megajuegos.independencia.exceptions;

public class WrongRoleException extends RuntimeException{

    public WrongRoleException(){
        super("El rol seleccionado es incorrecto");
    }
}
