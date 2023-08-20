package com.megajuegos.independencia.repository.data;

import com.megajuegos.independencia.entities.data.PlayerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerDataRepository extends JpaRepository<PlayerData, Long> {

    Optional<PlayerData> findById(Long id);
}
