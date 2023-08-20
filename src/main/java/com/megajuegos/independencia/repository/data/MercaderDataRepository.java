package com.megajuegos.independencia.repository.data;

import com.megajuegos.independencia.entities.data.MercaderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercaderDataRepository extends JpaRepository<MercaderData, Long> {
}
