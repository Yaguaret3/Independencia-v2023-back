package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.card.RepresentationCard;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import com.megajuegos.independencia.enums.VoteTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private RevolucionarioData revolucionarioData;
    private VoteTypeEnum voteType;
    @OneToMany
    private List<RepresentationCard> representacion;
}
