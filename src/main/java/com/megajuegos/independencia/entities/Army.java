package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.card.BattleCard;
import com.megajuegos.independencia.entities.data.CapitanData;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Army {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CapitanData capitanData;
    @ManyToOne
    private GameSubRegion gameSubRegion;

    @OneToMany
    private List<BattleCard> cartasJugadas;
    private boolean ataque;
    private Integer valorAzar;
    private Integer valorProvisorio;
}
