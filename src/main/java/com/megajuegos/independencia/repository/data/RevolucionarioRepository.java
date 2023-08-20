package com.megajuegos.independencia.repository.data;

import com.megajuegos.independencia.entities.data.RevolucionarioData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevolucionarioRepository extends JpaRepository<RevolucionarioData, Long> {
}
