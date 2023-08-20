package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.Camp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampTinyResponse {

    private Long campId;
    private Integer nivel;
    private Long gameAreaId;
    private Long capitanId;

    public static CampTinyResponse toTinyResponse(Camp entity){

        return CampTinyResponse.builder()
                .campId(entity.getId())
                .nivel(entity.getNivel())
                .gameAreaId(entity.getId())
                .capitanId(entity.getCapitanData().getId())
                .build();
    }
}
