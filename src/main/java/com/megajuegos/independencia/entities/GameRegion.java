package com.megajuegos.independencia.entities;

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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RegionEnum regionEnum;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GameSubRegion> subRegions;

    @OneToMany
    private List<Action> defenseActions;
}
