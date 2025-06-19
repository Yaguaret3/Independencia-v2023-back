package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.entities.data.PlayerData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Card {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private PlayerData playerData; // owning side
    private boolean alreadyPlayed = Boolean.FALSE;
    private Integer turnWhenPlayed;
}
