package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.enums.SubRegionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<SubRegionEnum> subregions;
    private Long tradeScore;

}
