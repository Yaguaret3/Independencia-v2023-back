package com.megajuegos.independencia.dto.response;

import com.megajuegos.independencia.entities.card.ExtraCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtraCardResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private String bonificacion;

    public static ExtraCardResponse toDtoResponse(ExtraCard extraCard){
        return ExtraCardResponse.builder()
                .id(extraCard.getId())
                .nombre(extraCard.getNombre())
                .descripcion(extraCard.getDescripcion())
                .bonificacion(extraCard.getBonificacion())
                .build();
    }

}
