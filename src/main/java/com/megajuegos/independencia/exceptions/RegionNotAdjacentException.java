package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.enums.RegionEnum;
import com.megajuegos.independencia.exceptions.common.CustomRuntimeException;

public class RegionNotAdjacentException extends CustomRuntimeException {

    public RegionNotAdjacentException(RegionEnum region1, RegionEnum region2){
        super(String.format("La región %s no es adyacente a la región %s", region1.getNombre(), region2.getNombre()));
    }
}
