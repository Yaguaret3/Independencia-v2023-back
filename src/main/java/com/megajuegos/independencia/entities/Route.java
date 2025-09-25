package com.megajuegos.independencia.entities;

import com.megajuegos.independencia.entities.card.MarketCard;
import com.megajuegos.independencia.entities.data.MercaderData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<GameSubRegion> subregions; // owning side
    private Long tradeScore;
    private Integer turn;
    private String comentario;
    @ManyToOne // owning side
    private MercaderData mercader;
    @OneToMany
    private List<MarketCard> markets;

}
