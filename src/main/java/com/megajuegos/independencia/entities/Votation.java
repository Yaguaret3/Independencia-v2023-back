package com.megajuegos.independencia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "votation")
    private List<Vote> votes;
    @ManyToOne
    private Congreso congreso; // owning side
    private Integer turnoDeJuego;
    private Boolean active;
}
