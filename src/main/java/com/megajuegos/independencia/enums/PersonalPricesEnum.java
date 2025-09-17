package com.megajuegos.independencia.enums;

import com.megajuegos.independencia.entities.PersonalPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PersonalPricesEnum {

    // GOBERNADOR
    CUARTEL(1, "Cuartel", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    MARKET(2, "Mercado", RoleEnum.GOBERNADOR,false, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .comercial(1)
                    .plata(4)
                    .build();
        }
    },
    ADMINISTRACION(3, "Administración", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    IGLESIA(4, "Iglesia", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    ACUEDUCTO(5, "Acueducto", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    ESCUELA(6, "Escuela", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    HOSPITAL(7, "Hospital", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    MONUMENTO(8, "Monumento", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    CIRCO(9, "Circo", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    RECAUDADOR(10, "Secretaría de finanzas", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    MURO(11, "Muro", RoleEnum.GOBERNADOR,true, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .construccion(1)
                    .plata(4)
                    .build();
        }
    },
    MILICIA(12, "Milicia", RoleEnum.GOBERNADOR,false, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .textil(1)
                    .plata(4)
                    .build();
        }
    },

    // MERCADER
    TEXTIL(13, "Textil", RoleEnum.MERCADER,false, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .puntajeComercial(3)
                    .build();
        }
    },
    AGROPECUARIA(14, "Agropecuaria", RoleEnum.MERCADER,false, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .puntajeComercial(2)
                    .build();
        }
    },
    METALMECANICA(15, "Metalmecánica", RoleEnum.MERCADER,false, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .puntajeComercial(1)
                    .build();
        }
    },
    CONSTRUCCION(16, "Construcción", RoleEnum.MERCADER,false, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .puntajeComercial(4)
                    .build();
        }
    },
    COMERCIAL(17, "Comercial", RoleEnum.MERCADER,false, false, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .puntajeComercial(5)
                    .build();
        }
    },
    /** Todavía no lo usamos TRADER_PRICES(RoleEnum.MERCADER,19, false, false, false), **/

    // CAPITÁN
    // ActionCards
    MOVIMIENTO(18, "Movimiento", RoleEnum.CAPITAN,false, true, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .agropecuaria(1)
                    .build();
        }
    },
    ATAQUE(19, "Ataque", RoleEnum.CAPITAN,false, true, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .agropecuaria(1)
                    .build();
        }
    },
    DEFENSA(20, "Defensa", RoleEnum.CAPITAN,false, true, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .agropecuaria(1)
                    .build();
        }
    },
    REACCION(21, "Reacción", RoleEnum.CAPITAN,false, true, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .agropecuaria(1)
                    .build();
        }
    },
    DESPLIEGUE(22, "Despliegue", RoleEnum.CAPITAN,false, true, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .agropecuaria(1)
                    .build();
        }
    },
    ACAMPE(23, "Acampe", RoleEnum.CAPITAN,false, true, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .agropecuaria(1)
                    .build();
        }
    },
    /*
    NADA(24, RoleEnum.CAPITAN,false, true, false) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .build();
        }
    },

     */
    // BattleCards
    CARGA_DE_INFANTERIA(25, "Carga de infantería", RoleEnum.CAPITAN,false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    },

    FUEGO_DE_ARTILLERIA(26, "Fuego de artillería", RoleEnum.CAPITAN, false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    },
    FLANQUEO_DE_CABALLERIA(27, "Flanqueo de caballería", RoleEnum.CAPITAN, false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    },
    //ANULA EFECTO
    MOVIMIENTO_EN_PINZAS(28, "Movimiento de pinzas", RoleEnum.CAPITAN, false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    },
    FALSA_RETIRADA(29, "Falsa retirada", RoleEnum.CAPITAN, false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    },
    REAGRUPAMIENTO(30, "Reagrupamiento", RoleEnum.CAPITAN, false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    },
    ADAPTACION(31, "Adaptación", RoleEnum.CAPITAN, false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    },
    RETIRADA_ORDENADA(32, "Retirada ordenada", RoleEnum.CAPITAN, false, false, true) {
        @Override
        public PersonalPrice getInitialPrices() {
            return PersonalPrice.builder()
                    .metalmecanica(1)
                    .build();
        }
    };

    private final int id;
    private final String nameToShow;
    private final RoleEnum rol;
    private final boolean isBuilding;
    private final boolean isActionOrder;
    private final boolean isBattleOrder;

    public static PersonalPricesEnum fromId(Integer id) throws InstanceNotFoundException {

        for (PersonalPricesEnum price : PersonalPricesEnum.values()) {
            if (Objects.equals(price.getId(), id)) {
                return price;
            }
        }
        throw new InstanceNotFoundException("No existe un precio con esa id");
    }

    public abstract PersonalPrice getInitialPrices();
}
