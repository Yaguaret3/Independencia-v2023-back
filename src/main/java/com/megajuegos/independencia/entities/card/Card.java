package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.entities.data.PlayerData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Card {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "playerData")
    private PlayerData playerData;

    @NotNull
    private boolean alreadyPlayed = Boolean.FALSE;
    @NotNull
    private Integer turnWhenPlayed;
}
