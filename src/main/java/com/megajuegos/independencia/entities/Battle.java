package com.megajuegos.independencia.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "battle")
    private List<Army> combatientes;
    @ManyToOne
    private GameSubRegion subregion; //owning side
    private Integer turnoDeJuego;
    private Boolean active;
}
