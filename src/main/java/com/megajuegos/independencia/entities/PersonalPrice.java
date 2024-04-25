package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.PersonalPricesEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Builder
public class PersonalPrice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PersonalPricesEnum name;
    @ManyToOne
    private PlayerData playerData; // owning side
    private Integer plata;
    private Integer textil;
    private Integer agropecuaria;
    private Integer metalmecanica;
    private Integer construccion;
    private Integer comercial;
    private Integer puntajeComercial;
}
