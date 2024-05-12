package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.GameRegion;
import com.megajuegos.independencia.enums.PhaseEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "gameData")
    private List<PlayerData> players;

    @OneToMany(mappedBy = "gameData")
    private List<GameRegion> gameRegions;

    @OneToMany(mappedBy = "gameData")
    private List<Congreso> congresos;

    private Integer turno;

    private Long nextEndOfTurn;

    @Enumerated(EnumType.STRING)
    private PhaseEnum fase;
    private boolean active;

    @CreationTimestamp
    private Instant createdOn;
}
