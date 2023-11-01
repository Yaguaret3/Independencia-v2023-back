package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.card.BattleCard;
import lombok.*;

import javax.persistence.*;
import java.util.List;
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
    private List<BattleCard> cartasDeCombate;
    @ManyToMany
    @JoinColumn
    private List<Army> otrosEjercitos;
    @ManyToOne
    private Army ejercitoAtaque;
    @ManyToOne
    private Army ejercitoDefensa;
    private Integer ataque;
    private Integer defensa;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameArea")
    private GameSubRegion gameSubRegion;
    private Integer turnoDeJuego;
    private Boolean active;
}
