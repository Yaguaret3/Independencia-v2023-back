package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.enums.ActionTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ActionCard extends Card {

    private ActionTypeEnum tipoAccion;
}
