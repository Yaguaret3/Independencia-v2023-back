package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.RevolucionarioData;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Congreso{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "congreso")
    private Set<RevolucionarioData> revolucionarioData;
    @OneToOne
    private RevolucionarioData presidente;
    @OneToMany
    @JoinColumn(name = "congreso")
    private Set<Votation> votations;
    private Integer plata;
    private Integer milicia;
}
