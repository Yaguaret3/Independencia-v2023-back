package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.Action;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionResponse {

    private Long id;
    private Long capitanId;
    private String capitanName;
    private String actionType;
    private boolean solved;
    private Long gameRegionId;
    private String gameRegionName;
    private Long gameSubregionId;
    private String gameSubregionName;

    public static ActionResponse toDto(Action entity){

        return ActionResponse.builder()
                .id(entity.getId())
                .capitanId(entity.getCapitanId().getId())
                .capitanName(entity.getCapitanId().getUser().getUsername())
                .actionType(entity.getActionType().getNombre())
                .gameRegionId(entity.getGameRegion() != null ? entity.getGameRegion().getId() : null)
                .gameRegionName(entity.getGameRegion() != null ? entity.getGameRegion().getRegionEnum().getNombre() : null)
                .gameSubregionId(entity.getSubregion() != null ? entity.getSubregion().getId() : null)
                .gameSubregionName(entity.getSubregion() != null ? entity.getSubregion().getNombre() : null)
                .build();
    }
}
