package com.megajuegos.independencia.enums;

import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.data.*;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public enum RoleEnum {


    ADMIN(1){
        @Override
        public PlayerData createPlayerData() {
            return null;
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            return null;
        }
    },
    USER(2){
        @Override
        public PlayerData createPlayerData() {
            return null;
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            return null;
        }
    },
    CONTROL(3){
        @Override
        public PlayerData createPlayerData() {
            return ControlData.builder()
                    .siguienteFaseSolicitada(false)
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            return null;
        }
    },
    GOBERNADOR(4){
        @Override
        public PlayerData createPlayerData() {

            List<PersonalPrice> prices = Arrays.stream(PersonalPricesEnum.values())
                    .filter(p -> p.getRol().equals(this))
                    .map(p -> PersonalPrice.builder()
                            .agropecuaria(0)
                            .textil(0)
                            .plata(0)
                            .metalmecanica(0)
                            .construccion(0)
                            .comercial(0)
                            .name(p)
                            .build())
                    .collect(Collectors.toList());

            return GobernadorData.builder()
                    .milicia(0)
                    .plata(0)
                    .prices(prices)
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            if(currentPlayerData instanceof GobernadorData){
                return null;
            }
            return currentPlayerData;
        }
    },
    CAPITAN(5){
        @Override
        public PlayerData createPlayerData() {
            List<PersonalPrice> prices = Arrays.stream(PersonalPricesEnum.values())
                    .filter(p -> p.getRol().equals(this))
                    .map(p -> PersonalPrice.builder()
                            .comercial(0)
                            .construccion(0)
                            .metalmecanica(0)
                            .textil(0)
                            .agropecuaria(0)
                            .plata(0)
                            .name(p)
                            .build())
                    .collect(Collectors.toList());

            return CapitanData.builder()
                    .prices(prices)
                    .reserva(0)
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            if(currentPlayerData instanceof CapitanData){
                return null;
            }
            return currentPlayerData;
        }
    },
    MERCADER(6){
        @Override
        public PlayerData createPlayerData() {
            List<PersonalPrice> prices = Arrays.stream(PersonalPricesEnum.values())
                    .filter(p -> p.getRol().equals(this))
                    .map(p -> PersonalPrice.builder()
                            .plata(0)
                            .puntajeComercial(0)
                            .name(p)
                            .build())
                    .collect(Collectors.toList());

            return MercaderData.builder()
                    .prices(prices)
                    .puntajeComercial(0)
                    .puntajeComercialAcumulado(0)
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            if(currentPlayerData instanceof MercaderData){
                return null;
            }
            return currentPlayerData;
        }
    },
    REVOLUCIONARIO(7){
        @Override
        public PlayerData createPlayerData() {
            return RevolucionarioData.builder()
                    .prices(Collections.emptyList())
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            if(currentPlayerData instanceof RevolucionarioData){
                return null;
            }
            return currentPlayerData;
        }
    },;

    private final Integer id;

    RoleEnum(Integer id){
        this.id = id;
    }

    public RoleEnum fromId(Integer id) throws InstanceNotFoundException {
        for(RoleEnum r : RoleEnum.values()){
            if(Objects.equals(r.getId(), id)){
                return r;
            }
        }
        throw new InstanceNotFoundException("No existe un rol con esa id");
    }

    public abstract PlayerData createPlayerData();
    public abstract PlayerData removePlayerData(PlayerData currentPlayerData);
}
