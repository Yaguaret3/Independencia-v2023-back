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
    private Integer plata=0;
    private Integer textil=0;
    private Integer agropecuaria=0;
    private Integer metalmecanica=0;
    private Integer construccion=0;
    private Integer comercial=0;
    private Integer puntajeComercial=0;
    private Integer disciplina=0;
}
