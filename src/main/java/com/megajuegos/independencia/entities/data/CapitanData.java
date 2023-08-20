package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.Army;
import com.megajuegos.independencia.entities.Camp;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CapitanData extends PlayerData {

    private Integer reserva=0;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "capitanData")
    private Set<Army> ejercito;
    private Integer disciplina=0;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "camp")
    private Camp camp;
}
