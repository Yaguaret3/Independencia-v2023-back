package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.entities.City;
import com.megajuegos.independencia.entities.Vote;
import com.megajuegos.independencia.enums.RepresentationEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RepresentationCard extends Card {

    private RepresentationEnum representacion;
    @ManyToOne
    private City city; //owning side

    @ManyToMany
    private List<Vote> votes; // owning side
}
