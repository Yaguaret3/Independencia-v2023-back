package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.GameRegion;
import com.megajuegos.independencia.enums.RegionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRegionRepository extends JpaRepository<GameRegion, Long> {

    Optional<GameRegion> findById(Long id);
    List<GameRegion> findByRegionEnum(RegionEnum regionEnum);
}
