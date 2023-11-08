package com.megajuegos.independencia.entities.card;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class ExtraCard extends Card {

    private String nombre;
    private String descripcion;
    private String bonificacion;
}
