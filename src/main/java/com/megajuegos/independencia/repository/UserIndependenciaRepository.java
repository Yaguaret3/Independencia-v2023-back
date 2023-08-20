package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.UserIndependencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIndependenciaRepository extends JpaRepository<UserIndependencia, Long> {

    Optional<UserIndependencia> findById(Long id);

    Optional<UserIndependencia> findByEmail(String email);

    boolean existsByEmail(String email);
}
