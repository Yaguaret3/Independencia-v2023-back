package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.enums.ActionTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ActionCard extends Card {

    @Enumerated(EnumType.STRING)
    private ActionTypeEnum tipoAccion;
}
