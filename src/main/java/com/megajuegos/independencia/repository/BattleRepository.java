package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    List<Battle> findByActive(boolean active);
}
