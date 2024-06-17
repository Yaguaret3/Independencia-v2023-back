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
    private int plata;
    private int textil;
    private int agropecuaria;
    private int metalmecanica;
    private int construccion;
    private int comercial;
    private int puntajeComercial;

    public PersonalPrice name(PersonalPricesEnum enumm){
        this.name = enumm;
        return this;
    }
}
