package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.data.GameData;
import com.megajuegos.independencia.entities.data.RevolucionarioData;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Congreso{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private GameData gameData;      // owning side
    @OneToMany(mappedBy = "congreso")
    private List<RevolucionarioData> revolucionarios;
    @OneToMany(mappedBy = "congreso")
    private List<Votation> votations;
    private Integer plata;
    private Integer milicia;
    @OneToOne
    private City sede; // owning side
}
