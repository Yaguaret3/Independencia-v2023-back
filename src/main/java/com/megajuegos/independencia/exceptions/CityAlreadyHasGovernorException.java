package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class CityAlreadyHasGovernorException extends CustomRuntimeException {

    public CityAlreadyHasGovernorException(String cityName, String gobernadorName){
        super(String.format("La ciudad %s ya está gobernada por %s", cityName, gobernadorName));
    }

}
