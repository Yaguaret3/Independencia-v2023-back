package com.megajuegos.independencia.repository.data;

import com.megajuegos.independencia.entities.data.ControlData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlDataRepository extends JpaRepository<ControlData, Long> {
}
