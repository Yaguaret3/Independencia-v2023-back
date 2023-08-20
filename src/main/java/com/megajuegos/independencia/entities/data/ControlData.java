package com.megajuegos.independencia.entities.data;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ControlData extends PlayerData{
}
