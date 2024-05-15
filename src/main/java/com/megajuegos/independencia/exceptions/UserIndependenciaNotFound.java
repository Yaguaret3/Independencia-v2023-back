package com.megajuegos.independencia.exceptions;

public class UserIndependenciaNotFound extends GenericNotFoundException{
    public UserIndependenciaNotFound(Long id) {
        super(id, "un usuario");
    }
}
