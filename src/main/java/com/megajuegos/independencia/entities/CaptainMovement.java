package com.megajuegos.independencia.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity @Getter @Setter
public class CaptainMovement {

    @Id @GeneratedValue
    private Long id;

    private Long captainId;

    private Long regionToId;
}
