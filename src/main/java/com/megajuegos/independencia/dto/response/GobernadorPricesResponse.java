package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GobernadorPricesResponse {

    List<PersonalPriceResponse> buildingPrices;
    PersonalPriceResponse marketPrice;
    PersonalPriceResponse militiaPrice;

    public static GobernadorPricesResponse toDto(Set<PersonalPrice> entities){

        return GobernadorPricesResponse.builder()
                .buildingPrices(entities.stream()
                        .filter(e -> e.getName().getIsBuilding())
                        .map(PersonalPriceResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .marketPrice(entities.stream()
                        .filter(e -> e.getName().equals(PersonalPricesEnum.MARKET))
                        .map(PersonalPriceResponse::toDtoResponse)
                        .findFirst()
                        .orElse(PersonalPriceResponse.builder().build())
                )
                .militiaPrice(entities.stream()
                        .filter(e -> e.getName().equals(PersonalPricesEnum.MILICIA))
                        .map(PersonalPriceResponse::toDtoResponse)
                        .findFirst()
                        .orElse(PersonalPriceResponse.builder().build()))
                .build();
    }
}
