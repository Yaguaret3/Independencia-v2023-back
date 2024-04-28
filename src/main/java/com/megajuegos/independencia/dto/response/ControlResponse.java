package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.data.ControlData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ControlResponse {

    private Long id;
    private String username;
    private boolean siguienteFaseSolicitada;

    public static ControlResponse toDto(ControlData controlData){

        return ControlResponse.builder()
                .id(controlData.getId())
                .username(controlData.getUser().getUsername())
                .siguienteFaseSolicitada(controlData.getSiguienteFaseSolicitada())
                .build();
    }
}
