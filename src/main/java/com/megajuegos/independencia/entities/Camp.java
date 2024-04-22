package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.CapitanData;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Camp {

    @Id @GeneratedValue
    private Long id;
    private Integer nivel;
    @ManyToOne
    private GameRegion gameRegion; //owning side
    @ManyToOne
    private GameSubRegion subregion; //owning side

    @OneToOne(mappedBy = "camp")
    private CapitanData capitanData;
}
