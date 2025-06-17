package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class UserIndependenciaNotFound extends GenericNotFoundException {
    public UserIndependenciaNotFound(Long id) {
        super(id, "un usuario");
    }
}
