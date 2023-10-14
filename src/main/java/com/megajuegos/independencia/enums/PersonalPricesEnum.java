package com.megajuegos.independencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PersonalPricesEnum {

    // GOBERNADOR
    CUARTEL(RoleEnum.GOBERNADOR,2, true, false, false),
    MARKET(RoleEnum.GOBERNADOR,3, false, false, false),
    ADMINISTRACION(RoleEnum.GOBERNADOR,4, true, false, false),
    IGLESIA(RoleEnum.GOBERNADOR,5, true, false, false),
    ACUEDUCTO(RoleEnum.GOBERNADOR,6, true, false, false),
    ESCUELA(RoleEnum.GOBERNADOR,7, true, false, false),
    HOSPITAL(RoleEnum.GOBERNADOR,8, true, false, false),
    MONUMENTO(RoleEnum.GOBERNADOR,9, true, false, false),
    CIRCO(RoleEnum.GOBERNADOR,10, true, false, false),
    RECAUDADOR(RoleEnum.GOBERNADOR,11, true, false, false),
    MURO(RoleEnum.GOBERNADOR,12, true, false, false),
    MILICIA(RoleEnum.GOBERNADOR,13, false, false, false),

    // MERCADER
    /** Quitar ResourcePrice y ResourceTypeEnum? */ TEXTIL(RoleEnum.MERCADER,14, false, false, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ AGROPECUARIA(RoleEnum.MERCADER,15, false, false, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ METALMECANICA(RoleEnum.MERCADER,16, false, false, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ CONSTRUCCION(RoleEnum.MERCADER,17, false, false, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ COMERCIAL(RoleEnum.MERCADER,18, false, false, false),
    TRADER_PRICES(RoleEnum.MERCADER,19, false, false, false),

    // CAPITÁN
    // ActionCards
    MOVIMIENTO(RoleEnum.CAPITAN,20, false, true, false),
    ATAQUE(RoleEnum.CAPITAN,21, false, true, false),
    DEFENSA(RoleEnum.CAPITAN,22, false, true, false),
    REACCION(RoleEnum.CAPITAN,23, false, true, false),
    DESPLIEGUE(RoleEnum.CAPITAN,24, false, true, false),
    ACAMPE(RoleEnum.CAPITAN,25, false, true, false),
    NADA(RoleEnum.CAPITAN,26, false, true, false),
    // BattleCards
    /** Cada una? */ BATTLE_CARD(RoleEnum.CAPITAN,27, false, false, false),
    DISCIPLINE_SPENT(RoleEnum.CAPITAN,28, false, false, false);


    private RoleEnum rol;
    private Integer id;
    private Boolean isBuilding;
    private Boolean isActionOrder;
    private Boolean isBattleOrder;

    public static PersonalPricesEnum fromId(Integer id) throws InstanceNotFoundException {

        for(PersonalPricesEnum price : PersonalPricesEnum.values()){
            if(Objects.equals(price.getId(), id)){
                return price;
            }
        }
        throw new InstanceNotFoundException("No existe un precio con esa id");
    }
}
