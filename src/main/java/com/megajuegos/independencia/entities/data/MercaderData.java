package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.Route;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MercaderData extends PlayerData {

    private Integer puntajeComercial;
    private Integer puntajeComercialAcumulado;
    @OneToMany
    private List<Route> routes;
}
