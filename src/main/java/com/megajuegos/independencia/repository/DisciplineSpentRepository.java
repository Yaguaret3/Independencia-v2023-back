package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.DisciplineSpent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineSpentRepository extends JpaRepository<DisciplineSpent, Long> {
}
