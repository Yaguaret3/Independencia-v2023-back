package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.enums.ActionTypeEnum;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    @Id
    private Long id;
    @ManyToOne
    private CapitanData capitanId;
    private ActionTypeEnum actionType;
    private boolean solved;
}
