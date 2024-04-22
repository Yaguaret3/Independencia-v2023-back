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

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private RevolucionarioData revolucionarioData; // owning side
    private VoteTypeEnum voteType;
    @ManyToMany(mappedBy = "votes")
    private List<RepresentationCard> representacion;
    @ManyToOne
    private Votation votation; // owning side
}
