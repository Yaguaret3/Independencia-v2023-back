package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.enums.BuildingTypeEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BuildingTypeEnum buildingType;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "city")
    private City city;
}
