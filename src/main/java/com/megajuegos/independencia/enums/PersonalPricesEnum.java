package com.megajuegos.independencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PersonalPricesEnum {

    // GOBERNADOR
    CUARTEL(RoleEnum.GOBERNADOR,2, true),
    MARKET(RoleEnum.GOBERNADOR,3, false),
    ADMINISTRACION(RoleEnum.GOBERNADOR,4, true),
    IGLESIA(RoleEnum.GOBERNADOR,5, true),
    ACUEDUCTO(RoleEnum.GOBERNADOR,6, true),
    ESCUELA(RoleEnum.GOBERNADOR,7, true),
    HOSPITAL(RoleEnum.GOBERNADOR,8, true),
    MONUMENTO(RoleEnum.GOBERNADOR,9, true),
    CIRCO(RoleEnum.GOBERNADOR,10, true),
    RECAUDADOR(RoleEnum.GOBERNADOR,11, true),
    MURO(RoleEnum.GOBERNADOR,12, true),
    MILICIA(RoleEnum.GOBERNADOR,13, false),

    // MERCADER
    /** Quitar ResourcePrice y ResourceTypeEnum? */ TEXTIL(RoleEnum.MERCADER,14, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ AGROPECUARIA(RoleEnum.MERCADER,15, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ METALMECANICA(RoleEnum.MERCADER,16, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ CONSTRUCCION(RoleEnum.MERCADER,17, false),
    /** Quitar ResourcePrice y ResourceTypeEnum? */ COMERCIAL(RoleEnum.MERCADER,18, false),
    TRADER_PRICES(RoleEnum.MERCADER,19, false),

    // CAPITÁN
    CAMP(RoleEnum.CAPITAN,20, true),
    /** Cada una? */ ACCION_CARD(RoleEnum.CAPITAN,21, false),
    /** Cada una? */ BATTLE_CARD(RoleEnum.CAPITAN,22, false),
    DISCIPLINE_SPENT(RoleEnum.CAPITAN,23, false);


    private RoleEnum rol;
    private Integer id;
    private Boolean isBuilding;

    public static PersonalPricesEnum fromId(Integer id) throws InstanceNotFoundException {

        for(PersonalPricesEnum price : PersonalPricesEnum.values()){
            if(Objects.equals(price.getId(), id)){
                return price;
            }
        }
        throw new InstanceNotFoundException("No existe un precio con esa id");
    }
}
