package com.megajuegos.independencia.enums;

import lombok.Getter;

@Getter
public enum LogTypeEnum {
    ENVIADO (">>>"), RECIBIDO("<<<");

    private String symbol;

    LogTypeEnum(String symbol){
        this.symbol = symbol;
    }
}
