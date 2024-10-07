package com.construccion.ciudades.construccion.repository;

import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import com.construccion.ciudades.construccion.model.generador.Genera_recuso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneraRecursoRepository extends JpaRepository<Genera_recuso, Long> {
    List<Genera_recuso> findByCiudad(Ciudad ciudad);
}
