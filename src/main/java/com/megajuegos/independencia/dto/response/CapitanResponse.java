package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.util.PlayerDataCardsUtil;
import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.enums.LogTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class CapitanResponse {

    private Long id;
    private Integer reserva;
    private List<ArmyFullResponse> ejercito;
    private CampResponse campamento;
    private List<ActionCardResponse> actionCards;
    private List<BattleCardFullResponse> battleCards;
    private List<ResourceCardResponse> recursos;
    private CapitanPricesResponse prices;
    private List<LogResponse> historial;

    public static CapitanResponse toDtoResponse(CapitanData entity){

        PlayerDataCardsUtil util = new PlayerDataCardsUtil(entity);

        List<LogResponse> historial = entity.getLogs().stream()
                .map(LogResponse::toResponse)
                .collect(Collectors.toList());
        Collections.reverse(historial);

        return CapitanResponse.builder()
                .id(entity.getId())
                .reserva(entity.getReserva())
                .ejercito(entity.getEjercito()
                        .stream()
                        .map(ArmyFullResponse::toDtoResponse)
                        .collect(Collectors.toList()))

                .campamento(CampResponse.toDtoResponse(entity.getCamp()))
                .actionCards(util.getActionCardList()
                        .stream()
                        .filter(a -> !a.isAlreadyPlayed())
                        .map(ActionCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .battleCards(util.getBattleCardList()
                        .stream()
                        .filter(b -> !b.isAlreadyPlayed())
                        .map(BattleCardFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .recursos((util.getResourceCardList()
                        .stream()
                        .map(ResourceCardResponse::toDtoResponse)
                        .collect(Collectors.toList())))
                .prices(CapitanPricesResponse.toDtoResponse(entity.getPrices()))
                .historial(historial)
                .build();

    }
}
