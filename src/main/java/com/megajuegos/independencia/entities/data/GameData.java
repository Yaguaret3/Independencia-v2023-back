package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.GameRegion;
import com.megajuegos.independencia.enums.PhaseEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameData")
    private Set<PlayerData> players;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameData")
    private Set<GameRegion> gameRegions;

    private Integer turno;

    private Long nextEndOfTurn;

    private PhaseEnum fase;
}
