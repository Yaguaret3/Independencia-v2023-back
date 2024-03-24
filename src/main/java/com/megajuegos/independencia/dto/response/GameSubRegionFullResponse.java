package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.GameSubRegion;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GameSubRegionFullResponse {

    private Long id;
    private String name;
    private CityFullResponse city;
    private List<ArmyFullResponse> ejercitos;
    private List<CampResponse> campamentos;
    private List<BattleFullResponse> batallas;

    public static GameSubRegionFullResponse toFullResponse(GameSubRegion entity){

        return GameSubRegionFullResponse.builder()
                .id(entity.getId())
                .name(entity.getSubRegionEnum().name())
                .city(entity.getCity() == null ? null :CityFullResponse.toDtoResponse(entity.getCity()))
                .ejercitos(entity.getEjercitos()
                        .stream()
                        .map(ArmyFullResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .campamentos(entity.getCampamentos()
                        .stream()
                        .map(CampResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .batallas(entity.getBatallas()
                        .stream()
                        .map(BattleFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .build();
    }

}
