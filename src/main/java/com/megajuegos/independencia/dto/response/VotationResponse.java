package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Votation;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class VotationResponse {

    private Long id;
    private List<VoteResponse> votos;
    private String propuesta;
    private Long congresoId;
    private Integer turnoDeJuego;
    private Boolean active;

    public static VotationResponse toDtoResponse(Votation entity){
        return VotationResponse.builder()
                .id(entity.getId())
                .propuesta(entity.getPropuesta())
                .votos(entity.getVotes()
                        .stream()
                        .map(VoteResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .congresoId(entity.getCongreso().getId())
                .turnoDeJuego(entity.getTurnoDeJuego())
                .active(entity.getActive())
                .build();
    }
}
