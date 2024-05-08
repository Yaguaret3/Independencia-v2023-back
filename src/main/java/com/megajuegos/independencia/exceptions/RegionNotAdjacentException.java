package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.enums.RegionEnum;

public class RegionNotAdjacentException extends RuntimeException{

    public RegionNotAdjacentException(RegionEnum region1, RegionEnum region2){
        super(String.format("La región %s no es adyacente a la región %s", region1.getNombre(), region2.getNombre()));
    }
}
