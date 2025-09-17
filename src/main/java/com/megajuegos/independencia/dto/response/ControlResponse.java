package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.data.ControlData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ControlResponse {

    private Long id;
    private String username;
    private boolean siguienteFaseSolicitada;
    private List<LogResponse> historial;

    public static ControlResponse toDto(ControlData controlData){

        List<LogResponse> historial = controlData.getLogs().stream()
                .map(LogResponse::toResponse)
                .collect(Collectors.toList());
        Collections.reverse(historial);

        return ControlResponse.builder()
                .id(controlData.getId())
                .username(controlData.getUser().getUsername())
                .siguienteFaseSolicitada(controlData.getSiguienteFaseSolicitada())
                .historial(historial)
                .build();
    }
}
