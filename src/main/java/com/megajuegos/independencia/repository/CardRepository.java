package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.card.Card;
import com.megajuegos.independencia.entities.card.ResourceCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(value = "SELECT r FROM ResourceCard r WHERE r.id IN :ids")
    List<ResourceCard> findResourceCardByIdIn(List<Long> ids);

}
