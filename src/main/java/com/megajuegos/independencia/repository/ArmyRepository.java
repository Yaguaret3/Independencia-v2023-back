package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.Army;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArmyRepository extends JpaRepository<Army, Long> {

    Optional<Army> findById(Long id);
}
