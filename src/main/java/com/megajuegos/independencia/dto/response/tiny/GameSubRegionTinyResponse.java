package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.GameSubRegion;
import com.megajuegos.independencia.enums.SubRegionEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class GameSubRegionTinyResponse {

    private Long id;
    private CityTinyResponse city;
    private String nombre;
    private String area;
    private String color;
    private List<ArmyTinyResponse> ejercitos;
    private List<CampTinyResponse> campamentos;
    private List<BattleTinyResponse> batallas;

    public static GameSubRegionTinyResponse toTinyResponse(GameSubRegion entity){
        return GameSubRegionTinyResponse.builder()
                .id(entity.getId())
                .city(entity.getCity()==null?null:CityTinyResponse.toTinyResponse(entity.getCity()))
                .nombre(entity.getNombre())
                .area(entity.getArea())
                .color(entity.getColor())
                .ejercitos(entity.getEjercitos()
                        .stream()
                        .map(ArmyTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .campamentos(entity.getCampamentos()
                        .stream()
                        .map(CampTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .batallas(entity.getBatallas()
                        .stream()
                        .map(BattleTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
