package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.CapitanData;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Army {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "capitanData")
    private CapitanData capitanData;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gameArea")
    private GameSubRegion gameSubRegion;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "ejercitos_con_batallas_join")
    private Set<Battle> batallas;
    private Integer cantidad=0;
}
