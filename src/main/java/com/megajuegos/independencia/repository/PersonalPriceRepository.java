package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.PersonalPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalPriceRepository extends JpaRepository<PersonalPrice, Long> {
}
