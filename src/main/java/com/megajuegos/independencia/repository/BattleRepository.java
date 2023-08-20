package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    @Query(value = "select b from Battle b left join fetch b.cartasDeCombate c LEFT JOIN FETCH " +
            "b.disciplinaUsada d LEFT JOIN FETCH b.ejercitos e " +
            "where b.id = :id")
    Optional<Battle> findById(Long id);
}
