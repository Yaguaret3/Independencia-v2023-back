package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class PlayerData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RoleEnum rol;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gameData")
    private GameData gameData;

    private String username="";

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "playerData")
    private Set<Card> cards;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "playerData")
    private Set<PersonalPrice> prices;
}
