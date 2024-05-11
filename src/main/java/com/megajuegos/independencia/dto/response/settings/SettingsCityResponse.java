package com.megajuegos.independencia.dto.response.settings;

import com.megajuegos.independencia.entities.City;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SettingsCityResponse {

    private Long id;
    private String nombre;

    public static SettingsCityResponse toSettingsReponse(City city){
        return SettingsCityResponse.builder()
                .id(city.getId())
                .nombre(city.getName())
                .build();
    }
}
