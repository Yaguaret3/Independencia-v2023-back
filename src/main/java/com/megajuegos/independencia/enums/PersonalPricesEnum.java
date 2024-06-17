package com.megajuegos.independencia.enums;

import com.megajuegos.independencia.entities.PersonalPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PersonalPricesEnum {

    // GOBERNADOR
    CUARTEL(1, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    MARKET(2, RoleEnum.GOBERNADOR,false, false, false, PersonalPrice.builder()
            .comercial(1)
            .plata(4)
            .build()),
    ADMINISTRACION(3, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    IGLESIA(4, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    ACUEDUCTO(5, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    ESCUELA(6, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    HOSPITAL(7, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    MONUMENTO(8, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    CIRCO(9, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    RECAUDADOR(10, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    MURO(11, RoleEnum.GOBERNADOR,true, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),
    MILICIA(12, RoleEnum.GOBERNADOR,false, false, false, PersonalPrice.builder()
            .construccion(1)
            .plata(4)
            .build()),

    // MERCADER
    TEXTIL(13, RoleEnum.MERCADER,false, false, false, PersonalPrice.builder()
            .puntajeComercial(3)
            .build()),
    AGROPECUARIA(14, RoleEnum.MERCADER,false, false, false, PersonalPrice.builder()
            .puntajeComercial(2)
            .build()),
    METALMECANICA(15, RoleEnum.MERCADER,false, false, false, PersonalPrice.builder()
            .puntajeComercial(1)
            .build()),
    CONSTRUCCION(16, RoleEnum.MERCADER,false, false, false, PersonalPrice.builder()
            .puntajeComercial(4)
            .build()),
    COMERCIAL(17, RoleEnum.MERCADER,false, false, false, PersonalPrice.builder()
            .puntajeComercial(5)
            .build()),
    /** Todavía no lo usamos TRADER_PRICES(RoleEnum.MERCADER,19, false, false, false), **/

    // CAPITÁN
    // ActionCards
    MOVIMIENTO(18, RoleEnum.CAPITAN,false, true, false, PersonalPrice.builder()
            .agropecuaria(1)
            .build()),
    ATAQUE(19, RoleEnum.CAPITAN,false, true, false, PersonalPrice.builder()
            .agropecuaria(1)
            .build()),
    DEFENSA(20, RoleEnum.CAPITAN,false, true, false, PersonalPrice.builder()
            .agropecuaria(1)
            .build()),
    REACCION(21, RoleEnum.CAPITAN,false, true, false, PersonalPrice.builder()
            .agropecuaria(1)
            .build()),
    DESPLIEGUE(22, RoleEnum.CAPITAN,false, true, false, PersonalPrice.builder()
            .agropecuaria(1)
            .build()),
    ACAMPE(23, RoleEnum.CAPITAN,false, true, false, PersonalPrice.builder()
            .agropecuaria(1)
            .build()),
    NADA(24, RoleEnum.CAPITAN,false, true, false, PersonalPrice.builder()
            .build()),
    // BattleCards
    CARGA_DE_INFANTERIA(25, RoleEnum.CAPITAN,false, false, true, PersonalPrice.builder()
            .metalmecanica(1)
            .build());

    private final int id;
    private final RoleEnum rol;
    private final boolean isBuilding;
    private final boolean isActionOrder;
    private final boolean isBattleOrder;
    private final PersonalPrice personalPrice;

    public static PersonalPricesEnum fromId(Integer id) throws InstanceNotFoundException {

        for (PersonalPricesEnum price : PersonalPricesEnum.values()) {
            if (Objects.equals(price.getId(), id)) {
                return price;
            }
        }
        throw new InstanceNotFoundException("No existe un precio con esa id");
    }
}
