package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.data.GameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameDataRepository extends JpaRepository<GameData, Long> {

    Optional<GameData> findFirstByOrderByIdDesc();
}
