package com.megajuegos.independencia.dto.response.util;

import com.megajuegos.independencia.entities.data.PlayerData;
import com.megajuegos.independencia.entities.card.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerDataCardsUtil {

    private List<ActionCard> actionCardList = new ArrayList<>();
    private List<BattleCard> battleCardList = new ArrayList<>();
    private List<ExtraCard> extraCardList = new ArrayList<>();
    private List<MarketCard> marketCardList = new ArrayList<>();
    private List<RepresentationCard> representationCardList = new ArrayList<>();
    private List<ResourceCard> resourceCardList = new ArrayList<>();

    public PlayerDataCardsUtil(PlayerData data){

        for(Card card : data.getCards()){
            if(card instanceof ActionCard){
                this.actionCardList.add((ActionCard) card);
            }
            if(card instanceof BattleCard){
                this.battleCardList.add((BattleCard) card);
            }
            if(card instanceof ExtraCard){
                this.extraCardList.add((ExtraCard) card);
            }
            if(card instanceof MarketCard){
                this.marketCardList.add((MarketCard) card);
            }
            if(card instanceof RepresentationCard && !card.isAlreadyPlayed()){
                this.representationCardList.add((RepresentationCard) card);
            }
            if(card instanceof ResourceCard){
                this.resourceCardList.add((ResourceCard) card);
            }
        }

    }

}
