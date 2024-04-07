package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.RevolucionarioData;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private List<RevolucionarioData> revolucionarios;
    @OneToOne
    private RevolucionarioData presidente;
    @OneToMany
    @JoinColumn(name = "congreso")
    private List<Votation> votations;
    private Integer plata;
    private Integer milicia;
    @OneToOne
    @JoinColumn
    private City sede;
}
