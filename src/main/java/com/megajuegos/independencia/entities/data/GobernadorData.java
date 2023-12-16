package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.City;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GobernadorData extends PlayerData {

    @OneToOne
    @JoinColumn
    private City city;
    private Integer plata;
    private Integer milicia;
}
