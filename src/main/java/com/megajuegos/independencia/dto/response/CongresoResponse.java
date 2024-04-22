package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.tiny.CityTinyResponse;
import com.megajuegos.independencia.dto.response.tiny.PlayerDataTinyResponse;
import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class CongresoResponse {

    private Long id;
    private List<PlayerDataTinyResponse> revolucionarios;
    private List<VotationResponse> votations;
    private Integer plata;
    private Integer militia;
    private String presidente;
    private Long presidenteId;
    private CityTinyResponse sede;

    public static CongresoResponse toDtoResponse(Congreso entity){

        return CongresoResponse.builder()
                .id(entity.getId())
                .plata(entity.getPlata())
                .militia(entity.getMilicia())
                .revolucionarios(entity.getRevolucionarios()
                        .stream()
                        .map(PlayerDataTinyResponse::toTinyResponse)
                        .collect(Collectors.toList()))
                .votations(entity.getVotations()
                        .stream()
                        .map(VotationResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .presidente(entity.getRevolucionarios().stream()
                        .filter(RevolucionarioData::isPresidente)
                        .findFirst()
                        .map(RevolucionarioData::getUser)
                        .map(UserIndependencia::getUsername)
                        .orElse(null))
                .presidenteId(entity.getRevolucionarios().stream()
                        .filter(RevolucionarioData::isPresidente)
                        .findFirst()
                        .map(RevolucionarioData::getId)
                        .orElse(null))
                .sede(CityTinyResponse.toTinyResponse(entity.getSede()))
                .build();
    }
}
