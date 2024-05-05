package com.megajuegos.independencia.repository;

import com.megajuegos.independencia.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByTurn(Integer turn);
}
