package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.PersonalPrice;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class CapitanPricesResponse {

    private List<PersonalPriceResponse> actionCardPrices;
    private List<PersonalPriceResponse> battleCardPrices;

    public static CapitanPricesResponse toDtoResponse(List<PersonalPrice> entities){

        List<PersonalPrice> actionCardPrices = entities.stream()
                .filter(p -> p.getName().isActionOrder())
                .collect(Collectors.toList());
        List<PersonalPrice> battleCardPrices = entities.stream()
                .filter(p -> p.getName().isBattleOrder())
                .collect(Collectors.toList());

        return CapitanPricesResponse.builder()
                .actionCardPrices(actionCardPrices.stream()
                        .map(PersonalPriceResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .battleCardPrices(battleCardPrices.stream()
                        .map(PersonalPriceResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
