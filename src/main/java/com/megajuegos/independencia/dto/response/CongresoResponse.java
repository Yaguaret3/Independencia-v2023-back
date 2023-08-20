package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
public class CongresoResponse {

    private Long id;
    private List<String> revolucionarios;
    private List<VotationResponse> votations;
    private Integer plata;
    private Integer militia;

    public static CongresoResponse toDtoResponse(Congreso entity){

        return CongresoResponse.builder()
                .id(entity.getId())
                .plata(entity.getPlata())
                .militia(entity.getMilicia())
                .revolucionarios(entity.getRevolucionarioData()
                        .stream()
                        .map(RevolucionarioData::getUsername)
                        .collect(Collectors.toList()))
                .votations(entity.getVotations()
                        .stream()
                        .map(VotationResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
