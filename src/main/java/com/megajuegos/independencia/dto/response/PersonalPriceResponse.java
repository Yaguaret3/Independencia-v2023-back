package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.PersonalPrice;
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
    private String bonification;
    private Integer plata;
    private Integer textil;
    private Integer agropecuaria;
    private Integer metalmecanica;
    private Integer construccion;
    private Integer comercial;
    private Integer puntajeComercial;
    private Integer disciplina;

    public static PersonalPriceResponse toDtoResponse(PersonalPrice entity){

        return PersonalPriceResponse.builder()
                .id(entity.getId())
                .name(entity.getName().name())
                .bonification(
                        Arrays.stream(BuildingTypeEnum.values())
                                .filter(bEnum -> bEnum.name().equals(entity.getName().name()))
                                .map(BuildingTypeEnum::getBonificacion)
                                .findFirst().orElse(""))
                .plata(entity.getPlata())
                .textil(entity.getTextil())
                .agropecuaria(entity.getAgropecuaria())
                .metalmecanica(entity.getMetalmecanica())
                .construccion(entity.getConstruccion())
                .comercial(entity.getComercial())
                .puntajeComercial(entity.getPuntajeComercial())
                .disciplina(entity.getDisciplina())
                .build();
    }
}
