package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.Action;
import com.megajuegos.independencia.entities.Army;
import com.megajuegos.independencia.entities.Camp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CapitanData extends PlayerData {

    private Integer reserva;

    @OneToMany(mappedBy = "capitanData")
    private List<Army> ejercito;
    @OneToMany(mappedBy = "capitanId")
    private List<Action> acciones;

    @OneToOne
    private Camp camp; //owning side
}
