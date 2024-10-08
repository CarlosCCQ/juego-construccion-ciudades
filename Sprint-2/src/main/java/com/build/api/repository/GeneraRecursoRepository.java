package com.build.api.repository;


import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.generador.Genera_recuso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneraRecursoRepository extends JpaRepository<Genera_recuso, Long> {
    List<Genera_recuso> findByCiudad(Ciudad ciudad);
}
