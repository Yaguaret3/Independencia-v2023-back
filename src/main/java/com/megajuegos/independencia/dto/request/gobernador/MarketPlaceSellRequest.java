package com.megajuegos.independencia.dto.request.gobernador;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class MarketPlaceSellRequest {

    Long idJugadorDestino;
    Long idMarketCard;
}
