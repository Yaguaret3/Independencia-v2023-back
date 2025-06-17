package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;

public class CityNotFoundException extends GenericNotFoundException {
    public CityNotFoundException(String city) {
        super(0L, String.format("la ciudad %s", city));
    }
    public CityNotFoundException(Long id){
        super(id, "una ciudad");
    }
}
