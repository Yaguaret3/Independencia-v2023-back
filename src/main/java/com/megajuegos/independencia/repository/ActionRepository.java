package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
