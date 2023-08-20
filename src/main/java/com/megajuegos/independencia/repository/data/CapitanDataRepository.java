package com.megajuegos.independencia.repository.data;

import com.megajuegos.independencia.entities.data.CapitanData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapitanDataRepository extends JpaRepository<CapitanData, Long> {
}
