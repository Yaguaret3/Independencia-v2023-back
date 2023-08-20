package com.megajuegos.independencia.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineSpent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long capitanId=0L;
    private Integer disciplineSpent=0;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "battle")
    private Battle battle;
}
