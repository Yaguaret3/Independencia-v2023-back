package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.enums.SubRegionEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GameSubRegion {

    @Id @GeneratedValue
    private Long id;
    @OneToOne
    private City city;                      // owning side
    private SubRegionEnum subRegionEnum;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<GameSubRegion> adjacent;
    @ManyToOne
    private GameRegion gameRegion;          // owning side
    private String nombre;
    private String area;
    private String color;
    @OneToMany(mappedBy = "subregion")
    private List<Army> ejercitos;
    @OneToMany(mappedBy = "subregion")
    private List<Camp> campamentos;
    @OneToMany(mappedBy = "subregion")
    private List<Battle> batallas;
    @OneToMany(mappedBy = "subregion")
    private List<Action> attackActions;
    @ManyToMany(mappedBy = "subregions")
    private List<Route> rutas;
}
