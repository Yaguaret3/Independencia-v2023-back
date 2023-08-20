package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Vote;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class VoteResponse {

    private Long id;
    private String revolucionarioName;
    private String voteType;
    private List<RepresentationCardResponse> representacionResponse;

    public static VoteResponse toDtoResponse(Vote entity){
        return VoteResponse.builder()
                .id(entity.getId())
                .revolucionarioName(entity.getRevolucionarioData().getUsername())
                .voteType(entity.getVoteType().getLabel())
                .representacionResponse(entity.getRepresentacion()
                                .stream()
                                .map(RepresentationCardResponse::toDtoResponse)
                                .collect(Collectors.toList()))
                .build();
    }
}
