package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.GameSubRegion;
import com.megajuegos.independencia.entities.data.MercaderData;
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
    private List<GameSubRegionTinyResponse> adjacent;
    private List<ArmyTinyResponse> ejercitos;
    private List<CampTinyResponse> campamentos;
    private List<BattleTinyResponse> batallas;
    private Integer tradeScore;
    //private List<PlayerDataTinyResponse> merchants;

    public static GameSubRegionTinyResponse toTinyResponse(GameSubRegion entity){
        return GameSubRegionTinyResponse.builder()
                .id(entity.getId())
                .city(entity.getCity()==null?null:CityTinyResponse.toTinyResponse(entity.getCity()))
                .nombre(entity.getNombre())
                .area(entity.getArea())
                .color(entity.getColor())
                .adjacent(entity.getAdjacent().stream()
                        .map(a -> GameSubRegionTinyResponse.builder()
                                .id(a.getId())
                                .nombre(a.getNombre())
                                .build())
                        .collect(Collectors.toList()))
                .ejercitos(entity.getEjercitos().stream()
                        .map(ArmyTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .campamentos(entity.getCampamentos().stream()
                        .map(CampTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .batallas(entity.getBatallas().stream()
                        .map(BattleTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .build();
    }
    public GameSubRegionTinyResponse addTradeScore(Integer score){
        this.tradeScore = score;
        return this;
    }

    /*public GameSubRegionTinyResponse reveal(List<MercaderData> merchants){

        this.setMerchants(merchants
                .stream()
                .map(PlayerDataTinyResponse::toTinyResponse)
                .collect(Collectors.toList()));
        return this;
    }

     */
}
