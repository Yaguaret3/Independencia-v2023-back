package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.util.PlayerDataCardsUtil;
import com.megajuegos.independencia.entities.Army;
import com.megajuegos.independencia.entities.Route;
import com.megajuegos.independencia.entities.Vote;
import com.megajuegos.independencia.entities.data.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PlayerDataFullResponse {

    private Long id;
    private String username;
    private String rol;
    private List<MarketCardResponse> mercados;
    private List<ResourceCardResponse> recursos;
    private List<ExtraCardResponse> extras;
    private List<RepresentationCardResponse> representacion;
    private List<ActionCardResponse> actionCards;
    private List<BattleCardFullResponse> battleCards;
    private List<PersonalPriceResponse> prices;

    //Gobernador/Revolucionario
    private Integer plata;
    //Gobernador
    private Integer milicia;
    private CityFullResponse ciudad;
    //Capitan
    private Integer reserva;
    private List<ArmyFullResponse> armies;
    //Mercader
    private Integer puntajeComercial;
    private Integer puntajeComercialAcumulado;
    private List<RouteResponse> routes;
    //Revolucionario
    private CongresoResponse congreso;
    private List<VoteResponse> votes;

    public static PlayerDataFullResponse toFullResponse(PlayerData entity){

        PlayerDataCardsUtil util = new PlayerDataCardsUtil(entity);

        PlayerDataFullResponse response = PlayerDataFullResponse.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .rol(entity.getRol().name())
                .mercados(util.getMarketCardList()
                        .stream()
                        .map(MarketCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .recursos(util.getResourceCardList()
                        .stream()
                        .map(ResourceCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .extras(util.getExtraCardList()
                        .stream()
                        .map(ExtraCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .representacion(util.getRepresentationCardList()
                        .stream()
                        .map(RepresentationCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .actionCards(util.getActionCardList()
                        .stream()
                        .map(ActionCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .battleCards(util.getBattleCardList()
                        .stream()
                        .map(BattleCardFullResponse::toFullResponse)
                        .collect(Collectors.toList()))
                .prices(entity.getPrices().stream()
                        .map(PersonalPriceResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .build();

        switch (entity.getRol()){
            case GOBERNADOR:
                GobernadorData gobernadorData = (GobernadorData) entity;
                response.setPlata(gobernadorData.getPlata());
                response.setMilicia(gobernadorData.getMilicia());
                response.setCiudad(gobernadorData.getCity() == null ? null : CityFullResponse.toDtoResponse(gobernadorData.getCity()));
                break;
            case CAPITAN:
                CapitanData capitanData = (CapitanData) entity;
                response.setReserva(capitanData.getReserva());
                response.setArmies(capitanData.getEjercito().stream()
                        .map(ArmyFullResponse::toDtoResponse)
                        .collect(Collectors.toList()));
                break;
            case MERCADER:
                MercaderData mercaderData = (MercaderData) entity;
                response.setPuntajeComercial(mercaderData.getPuntajeComercial());
                response.setPuntajeComercialAcumulado(mercaderData.getPuntajeComercialAcumulado());
                response.setRoutes(mercaderData.getRoutes().stream()
                        .map(RouteResponse::toDto)
                        .collect(Collectors.toList()));
                break;
            case REVOLUCIONARIO:
                RevolucionarioData revolucionarioData = (RevolucionarioData) entity;
                response.setPlata(revolucionarioData.getPlata());
                response.setVotes(revolucionarioData.getVotos().stream()
                        .map(VoteResponse::toDtoResponse)
                        .collect(Collectors.toList()));
                response.setCongreso(CongresoResponse.toDtoResponse(revolucionarioData.getCongreso()));
                break;
            default:
        }
        return response;
    }
}
