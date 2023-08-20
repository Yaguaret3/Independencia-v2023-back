package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.Congreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CongresoRepository extends JpaRepository<Congreso, Long> {

    Optional<Congreso> findById(Long id);
}
