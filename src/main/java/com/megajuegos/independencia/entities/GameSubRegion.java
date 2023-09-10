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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private City city;
    private SubRegionEnum subRegionEnum;
    @ManyToMany
    @NotNull
    private List<GameSubRegion> adjacent;
    private String nombre;
    @Column(columnDefinition = "TEXT")
    private String area;
    private String color;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameSubRegion")
    private List<Army> ejercitos;
    @OneToMany(mappedBy = "gameSubRegion")
    private List<Camp> campamentos;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameSubRegion")
    private List<Battle> batallas;
    @OneToMany
    @JoinColumn
    private List<Action> attackActions;
}
