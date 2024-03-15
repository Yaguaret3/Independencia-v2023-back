package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.RepresentationCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepresentationCardResponse {

    private Long id;
    private String ciudad;
    private Integer poblacion;
    private VoteResponse vote;

    public static RepresentationCardResponse toDtoResponse(RepresentationCard entity){
        return RepresentationCardResponse.builder()
                .id(entity.getId())
                .ciudad(entity.getRepresentacion().getNombre())
                .poblacion(entity.getRepresentacion().getPoblacion())
                .vote(VoteResponse.toDtoResponse(entity.getVote()))
                .build();
    }
}
