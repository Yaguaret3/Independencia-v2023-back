package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.Log;
import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.UserIndependencia;
import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.enums.RoleEnum;
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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class PlayerData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum rol;

    @ManyToOne
    private GameData gameData; // owning side

    @ManyToOne
    private UserIndependencia user; // owning side

    @OneToMany(mappedBy = "playerData")
    private List<Card> cards;

    @OneToMany(mappedBy = "playerData")
    private List<PersonalPrice> prices;

    @OneToMany(mappedBy = "player")
    private List<Log> logs;
}
