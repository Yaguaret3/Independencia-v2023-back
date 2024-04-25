package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.GobernadorData;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer marketLevel;
    private Integer publicOpinion;
    private Integer taxesLevel;
    private Integer prestige;
    private String diputado;
    @OneToOne(mappedBy = "city")
    private GobernadorData gobernadorData;
    @OneToMany(mappedBy = "city")
    private List<Building> buildings;
    @OneToOne(mappedBy = "city")
    private GameSubRegion subRegion;
    @OneToOne(mappedBy = "sede")
    private Congreso sedeDelCongreso;
}
