package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.card.BattleCard;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "batalla")
    private Set<BattleCard> cartasDeCombate;
    @ManyToMany(mappedBy = "batallas")
    private Set<Army> ejercitos;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameArea")
    private GameSubRegion gameSubRegion;
    private Integer turnoDeJuego=0;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "battle")
    private Set<DisciplineSpent> disciplinaUsada;
    private Boolean active=true;
}
