package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Log;
import com.megajuegos.independencia.enums.LogTypeEnum;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogResponse {

    private String nota;
    private LogTypeEnum tipo;

    public static LogResponse toResponse(Log entity){

        return LogResponse.builder()
                .tipo(entity.getTipo())
                .nota(String.format("Turno %s %s %s",
                                entity.getTurno(),
                                entity.getTipo().getSymbol(),
                                entity.getNota()))
                .build();
    }
}
