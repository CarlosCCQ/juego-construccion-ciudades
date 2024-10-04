package com.juegoconstruccion.juego.construccion.ciudades.repository;

import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.model.Genera_recuso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneraRecursoRepository extends JpaRepository<Genera_recuso, Long> {
    List<Genera_recuso> findByCiudad(Ciudad ciudad);
}
