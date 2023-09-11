package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.entities.Battle;
import com.megajuegos.independencia.enums.BattleTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BattleCard extends Card {

    private BattleTypeEnum tipoOrdenDeBatalla;
}
