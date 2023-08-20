package com.megajuegos.independencia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Votation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String propuesta;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "votation")
    private Set<Vote> votes;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "congreso")
    private Congreso congreso;
    private Integer turnoDeJuego;
    private Boolean active;
}
