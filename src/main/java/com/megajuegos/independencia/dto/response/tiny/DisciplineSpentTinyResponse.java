package com.megajuegos.independencia.dto.response.tiny;

import com.megajuegos.independencia.entities.DisciplineSpent;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DisciplineSpentTinyResponse {

    private Long id;
    private Long capitanId;
    private Integer disciplineSpent;
    private Long battleId;

    public static DisciplineSpentTinyResponse toTinyResponse(DisciplineSpent entity){

        return DisciplineSpentTinyResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanId())
                .disciplineSpent(entity.getDisciplineSpent())
                .battleId(entity.getBattle().getId())
                .build();
    }
}
