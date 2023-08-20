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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city")
    private City city;
    private Integer plata=0;
    private Integer milicia=0;
}
