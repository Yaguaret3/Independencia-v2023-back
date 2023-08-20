package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.GobernadorData;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name="";
    private Integer marketLevel=0;
    private Integer publicOpinion=0;
    private Integer taxesLevel=0;
    private Integer prestige=0;
    private String diputado;
    @OneToOne(mappedBy = "city")
    private GobernadorData gobernadorData;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "city")
    private Set<Building> buildings;
    @OneToOne(mappedBy = "city")
    private GameSubRegion subRegion;
}
