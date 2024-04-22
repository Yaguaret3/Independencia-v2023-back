package com.megajuegos.independencia.entities.data;

import com.megajuegos.independencia.entities.Congreso;
import com.megajuegos.independencia.entities.Vote;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RevolucionarioData extends PlayerData {

    @OneToMany(mappedBy = "revolucionarioData")
    private List<Vote> votos;
    private Integer plata;
    @ManyToOne
    private Congreso congreso; // owning side

    private boolean presidente;
}
