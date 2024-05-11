package com.megajuegos.independencia.exceptions;

public class CityAlreadyHasGovernorException extends RuntimeException{

    public CityAlreadyHasGovernorException(String cityName, String gobernadorName){
        super(String.format("La ciudad %s ya está gobernada por %s", cityName, gobernadorName));
    }

}
