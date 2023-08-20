package com.megajuegos.independencia.entities.card;

import com.megajuegos.independencia.enums.ResourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class ResourceCard extends Card {

    private ResourceTypeEnum resourceTypeEnum;
}
