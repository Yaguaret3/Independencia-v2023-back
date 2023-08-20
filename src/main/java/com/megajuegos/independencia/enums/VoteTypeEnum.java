package com.megajuegos.independencia.enums;

import lombok.Getter;

import javax.management.InstanceNotFoundException;
import java.util.Objects;

@Getter
public enum VoteTypeEnum {

    A_FAVOR("A favor"),
    SE_ABSTIENE("Se abstiene"),
    EN_CONTRA("En contra");

    String label;

    VoteTypeEnum(String label){
        this.label = label;
    }
}
