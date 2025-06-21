package com.megajuegos.independencia.enums;

import com.megajuegos.independencia.entities.PersonalPrice;
import com.megajuegos.independencia.entities.data.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
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

        @Override
        public List<PersonalPrice> getInitialPrices() {
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

        @Override
        public List<PersonalPrice> getInitialPrices() {
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

        @Override
        public List<PersonalPrice> getInitialPrices() {
            return null;
        }
    },
    GOBERNADOR(4){
        @Override
        public PlayerData createPlayerData() {

            return GobernadorData.builder()
                    .milicia(0)
                    .plata(20)
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            if(currentPlayerData instanceof GobernadorData){
                return null;
            }
            return currentPlayerData;
        }

        @Override
        public List<PersonalPrice> getInitialPrices() {
            return Arrays.stream(PersonalPricesEnum.values())
                    .filter(p -> p.getRol().equals(this))
                    .map(p -> p.getInitialPrices().name(p))
                    .collect(Collectors.toList());
        }
    },
    CAPITAN(5){
        @Override
        public PlayerData createPlayerData() {
            return CapitanData.builder()
                    .reserva(3)
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            if(currentPlayerData instanceof CapitanData){
                return null;
            }
            return currentPlayerData;
        }

        @Override
        public List<PersonalPrice> getInitialPrices() {
            return Arrays.stream(PersonalPricesEnum.values())
                    .filter(p -> p.getRol().equals(this))
                    .map(p -> p.getInitialPrices().name(p))
                    .collect(Collectors.toList());
        }
    },
    MERCADER(6){
        @Override
        public PlayerData createPlayerData() {
            return MercaderData.builder()
                    .puntajeComercial(7)
                    .puntajeComercialAcumulado(7)
                    .build();
        }

        @Override
        public PlayerData removePlayerData(PlayerData currentPlayerData) {
            if(currentPlayerData instanceof MercaderData){
                return null;
            }
            return currentPlayerData;
        }

        @Override
        public List<PersonalPrice> getInitialPrices() {
            return Arrays.stream(PersonalPricesEnum.values())
                    .filter(p -> p.getRol().equals(this))
                    .map(p -> p.getInitialPrices().name(p))
                    .collect(Collectors.toList());
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

        @Override
        public List<PersonalPrice> getInitialPrices() {
            return Collections.emptyList();
        }
    },;

    private final Integer id;

    public abstract PlayerData createPlayerData();
    public abstract PlayerData removePlayerData(PlayerData currentPlayerData);

    public abstract List<PersonalPrice> getInitialPrices();
}
