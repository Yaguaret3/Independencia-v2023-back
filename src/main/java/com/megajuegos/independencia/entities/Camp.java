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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer nivel=0;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameRegion")
    private GameRegion gameRegion;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameSubRegion")
    private GameSubRegion gameSubRegion;


    @OneToOne(mappedBy = "camp")
    private CapitanData capitanData;
}
