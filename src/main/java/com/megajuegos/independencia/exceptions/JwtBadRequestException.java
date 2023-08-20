package com.megajuegos.independencia.exceptions;

public class JwtBadRequestException extends RuntimeException{

    public JwtBadRequestException(String s){
        super(s);
    }
}
