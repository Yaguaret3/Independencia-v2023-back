package com.megajuegos.independencia.dto.response.settings;

import com.megajuegos.independencia.entities.City;
import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.entities.data.GobernadorData;
import com.megajuegos.independencia.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SettingsUserResponse {

    private Long id;
    private String username;
    private String email;
    private String rol;
    private String city;

    public static SettingsUserResponse toSettingsResponse(UserIndependencia entity){

        String city = entity.getRoles().contains(RoleEnum.GOBERNADOR) ?
                entity.getPlayerDataList().stream()
                        .filter(pd -> pd.getRol().equals(RoleEnum.GOBERNADOR))
                        .findFirst()
                        .map(pd -> (GobernadorData) pd)
                        .map(GobernadorData::getCity)
                        .map(City::getName)
                        .orElse("")
                : null;

        return SettingsUserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .rol(entity.getRoles().stream().filter(r -> r != RoleEnum.USER).map(Enum::name).findFirst().orElse(""))
                .city(city)
                .build();
    }
}
