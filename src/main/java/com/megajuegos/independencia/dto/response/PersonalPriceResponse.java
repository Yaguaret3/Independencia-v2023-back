package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.enums.BattleTypeEnum;
import com.megajuegos.independencia.enums.BuildingTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.Collectors;

@Builder
@Data
public class PersonalPriceResponse {

    private Long id;
    private String name;
    private String nameToShow;
    private String bonification;
    private String descripcion;
    private Integer plata;
    private Integer textil;
    private Integer agropecuaria;
    private Integer metalmecanica;
    private Integer construccion;
    private Integer comercial;
    private Integer puntajeComercial;

    public static PersonalPriceResponse toDtoResponse(PersonalPrice entity){

        return PersonalPriceResponse.builder()
                .id(entity.getId())
                .name(entity.getName().name())
                .nameToShow(entity.getName().getNameToShow())
                .bonification(
                        Arrays.stream(BuildingTypeEnum.values())
                                .filter(bEnum -> bEnum.name().equals(entity.getName().name()))
                                .map(BuildingTypeEnum::getBonificacion)
                                .findFirst().orElse(""))
                .descripcion(
                        Arrays.stream(BattleTypeEnum.values())
                                .filter(bEnum -> bEnum.name().equals(entity.getName().name()))
                                .map(BattleTypeEnum::getEfecto)
                                .findFirst().orElse(""))
                .plata(entity.getPlata())
                .textil(entity.getTextil())
                .agropecuaria(entity.getAgropecuaria())
                .metalmecanica(entity.getMetalmecanica())
                .construccion(entity.getConstruccion())
                .comercial(entity.getComercial())
                .puntajeComercial(entity.getPuntajeComercial())
                .build();
    }
}
