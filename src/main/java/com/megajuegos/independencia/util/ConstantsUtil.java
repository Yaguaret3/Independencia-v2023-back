package com.megajuegos.independencia.util;

public class ConstantsUtil {

    private ConstantsUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static final String AUTHORIZACION_FOR_HEADER = "Authorization";
    public static final String STARTS_WITH_BEARER = "Bearer ";
    public static final int BEARER_PART_LENGHT = 7;
}
