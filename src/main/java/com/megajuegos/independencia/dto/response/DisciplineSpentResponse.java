package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.DisciplineSpent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisciplineSpentResponse {

    private Long id;
    private Long capitanId;
    private Integer disciplineSpent;
    private Long battleId;

    public static DisciplineSpentResponse toDtoResponse(DisciplineSpent entity){

        return DisciplineSpentResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanId())
                .disciplineSpent(entity.getDisciplineSpent())
                .battleId(entity.getBattle().getId())
                .build();
    }
}
