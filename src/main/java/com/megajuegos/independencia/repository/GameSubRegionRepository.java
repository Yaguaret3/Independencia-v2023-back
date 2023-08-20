package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.GameSubRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameSubRegionRepository extends JpaRepository<GameSubRegion, Long> {

    Optional<GameSubRegion> findById(Long id);
}
