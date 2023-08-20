package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.enums.SubRegionEnum;
import lombok.*;

import javax.persistence.*;
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

    private String nombre;
    @Column(columnDefinition = "TEXT")
    private String area;
    private String color;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameSubRegion")
    private Set<Army> ejercitos;
    @OneToMany(mappedBy = "gameSubRegion")
    private Set<Camp> campamentos;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameSubRegion")
    private Set<Battle> batallas;
}
