package com.megajuegos.independencia.dto.response.settings;

import com.megajuegos.independencia.entities.data.GameData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class SettingsGameDataResponse {

    private long id;
    private String nombre;
    private LocalDate createdOn;
    private boolean habilitado;
    private List<SettingsPlayerDataTinyResponse> players;

    public static SettingsGameDataResponse toSettingsResponse(GameData entity) {

        return SettingsGameDataResponse.builder()
                .createdOn(entity.getCreatedOn() == null ? null : entity.getCreatedOn().atZone(ZoneId.systemDefault()).toLocalDate())
                .id(entity.getId())
                .nombre(entity.getNombre())
                .habilitado(entity.isActive())
                .players(entity.getPlayers().stream()
                        .map(SettingsPlayerDataTinyResponse::toSettingsResponse)
                        .collect(Collectors.toList()))

                .build();
    }
}
