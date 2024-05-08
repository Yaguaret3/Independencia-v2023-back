package com.megajuegos.independencia.exceptions;

public class VoteNotFoundException extends GenericNotFoundException{
    public VoteNotFoundException(Long id) {
        super(id, "un voto");
    }
}
