package com.megajuegos.independencia.exceptions;

import com.megajuegos.independencia.enums.SubRegionEnum;
import com.megajuegos.independencia.exceptions.common.GenericNotFoundException;


public class RegionNotFoundException extends GenericNotFoundException {
    public RegionNotFoundException(Long id) {
        super(id, "una región");
    }
    public RegionNotFoundException(SubRegionEnum subRegionEnum) {
        super(0L, String.format("la subregión %s", subRegionEnum.getNombre()));
    }
    public RegionNotFoundException() {
        super(0L, "ninguna región");
    }
}
