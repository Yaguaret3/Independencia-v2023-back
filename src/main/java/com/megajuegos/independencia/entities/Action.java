package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.CapitanData;
import com.megajuegos.independencia.enums.ActionTypeEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private CapitanData capitanId; //owning side
    private ActionTypeEnum actionType;
    private boolean solved;

    @ManyToOne
    private GameRegion gameRegion; //owning side
    @ManyToOne
    private GameSubRegion subregion; //owning side
}
