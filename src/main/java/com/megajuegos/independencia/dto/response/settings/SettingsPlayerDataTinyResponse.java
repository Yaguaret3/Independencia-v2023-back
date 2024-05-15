package com.megajuegos.independencia.dto.response.settings;

import com.megajuegos.independencia.entities.data.GobernadorData;
import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SettingsPlayerDataTinyResponse {

    private Long id;
    private String username;
    private RoleEnum rol;
    private String ciudad;

    public static SettingsPlayerDataTinyResponse toSettingsResponse(PlayerData entity){

        String nombreCiudad = null;
        if(entity instanceof GobernadorData && ((GobernadorData) entity).getCity() != null){
            nombreCiudad = ((GobernadorData) entity).getCity().getName();
        }

        return SettingsPlayerDataTinyResponse.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .rol(entity.getRol())
                .ciudad(nombreCiudad)
                .build();
    }


}
