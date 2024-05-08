package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.enums.LogTypeEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nota;
    @Enumerated(EnumType.STRING)
    private LogTypeEnum tipo;
    private int turno;

    @ManyToOne
    private PlayerData player; //owning side
}
