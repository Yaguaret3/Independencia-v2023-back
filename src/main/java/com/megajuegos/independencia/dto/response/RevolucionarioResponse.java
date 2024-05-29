package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.dto.response.util.PlayerDataCardsUtil;
import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import com.megajuegos.independencia.enums.LogTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevolucionarioResponse {

    private Long id;
    private List<VoteResponse> votos;
    private Integer plata;
    private CongresoResponse congreso;
    private List<RepresentationCardResponse> representacion;
    private List<ResourceCardResponse> recursos;
    private List<ExtraCardResponse> extras;
    private List<LogResponse> historial;

    public static RevolucionarioResponse toDtoResponse(RevolucionarioData entity){

        PlayerDataCardsUtil util = new PlayerDataCardsUtil(entity);

        List<LogResponse> historial = entity.getLogs().stream()
                .map(LogResponse::toResponse)
                .collect(Collectors.toList());
        Collections.reverse(historial);

        return RevolucionarioResponse.builder()
                .id(entity.getId())
                .votos(entity.getVotos()
                        .stream()
                        .map(VoteResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .plata(entity.getPlata())
                .congreso(CongresoResponse.toDtoResponse(entity.getCongreso()))
                .representacion(util.getRepresentationCardList()
                        .stream()
                        .map(RepresentationCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .recursos(util.getResourceCardList()
                        .stream()
                        .map(ResourceCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .extras(util.getExtraCardList()
                        .stream()
                        .map(ExtraCardResponse::toDtoResponse)
                        .collect(Collectors.toList()))
                .historial(historial)
                .build();
    }
}
