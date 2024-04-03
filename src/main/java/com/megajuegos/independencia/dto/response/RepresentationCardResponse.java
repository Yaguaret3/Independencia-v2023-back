package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.RepresentationCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepresentationCardResponse {

    private Long id;
    private String ciudad;
    private Integer poblacion;
    private List<VoteResponse> votes;

    public static RepresentationCardResponse toDtoResponse(RepresentationCard entity){
        return RepresentationCardResponse.builder()
                .id(entity.getId())
                .ciudad(entity.getRepresentacion().getNombre())
                .poblacion(entity.getRepresentacion().getPoblacion())
                .votes(entity.getVotes().stream()
                        .map(VoteResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
