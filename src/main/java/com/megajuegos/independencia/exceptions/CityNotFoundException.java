package com.megajuegos.independencia.exceptions;

public class CityNotFoundException extends GenericNotFoundException{
    public CityNotFoundException(String city) {
        super(0L, String.format("la ciudad %s", city));
    }
    public CityNotFoundException(Long id){
        super(id, "una ciudad");
    }
}
