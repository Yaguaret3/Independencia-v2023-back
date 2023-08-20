package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.Votation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotationRepository extends JpaRepository<Votation, Long> {

    Optional<Votation> findById(Long id);
}
