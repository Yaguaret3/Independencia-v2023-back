package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.entities.Vote;
import com.megajuegos.independencia.enums.RepresentationEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RepresentationCard extends Card {

    private RepresentationEnum representacion;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vote")
    private Vote vote;
}
