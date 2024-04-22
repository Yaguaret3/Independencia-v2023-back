package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.GameData;
import com.megajuegos.independencia.enums.RegionEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameRegion {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private GameData gameData; // owning side

    private RegionEnum regionEnum;
    @OneToMany(mappedBy = "gameRegion")
    private List<GameSubRegion> subRegions;
    @OneToMany(mappedBy = "gameRegion")
    private List<Action> defenseActions;
}
