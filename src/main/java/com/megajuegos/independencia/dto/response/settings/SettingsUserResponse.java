package com.megajuegos.independencia.dto.response.settings;

import com.megajuegos.independencia.entities.UserIndependencia;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SettingsUserResponse {

    private Long id;
    private String username;
    private String email;

    public static SettingsUserResponse toSettingsResponse(UserIndependencia entity){

        return SettingsUserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }
}
