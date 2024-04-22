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

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private CapitanData capitanData; // owning side
    @ManyToOne
    private GameSubRegion subregion; // owning side
    @ManyToOne
    private Battle battle;          // owning side

    @OneToMany(mappedBy = "army")
    private List<BattleCard> cartasJugadas;
    private Integer milicias;
    private boolean ataque;
    private Integer valorAzar;
    private Integer valorProvisorio;

    @Transient
    private int iniciativa;
}
