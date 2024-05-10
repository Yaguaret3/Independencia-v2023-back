package com.megajuegos.independencia.dto.response.settings;

import com.megajuegos.independencia.entities.data.GameData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class SettingsGameDataResponse {

    private long id;
    private Instant createdOn;
    private List<SettingsPlayerDataTinyResponse> players;

    public static SettingsGameDataResponse toSettingsResponse(GameData entity) {

        return SettingsGameDataResponse.builder()
                .createdOn(entity.getCreatedOn())
                .id(entity.getId())
                .players(entity.getPlayers().stream()
                        .map(SettingsPlayerDataTinyResponse::toSettingsResponse)
                        .collect(Collectors.toList()))

                .build();
    }
}
