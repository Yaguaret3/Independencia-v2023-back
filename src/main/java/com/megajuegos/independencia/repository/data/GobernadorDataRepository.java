package com.megajuegos.independencia.repository.data;

import com.megajuegos.independencia.entities.data.GobernadorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GobernadorDataRepository extends JpaRepository<GobernadorData, Long> {
}
