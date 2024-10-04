package com.juegoconstruccion.juego.construccion.ciudades.repository;

import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.model.Genera_recuso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz que define los métodos para interactuar con la base de datos
 * relacionada con los generadores de recursos.
 *
 * Extiende JpaRepository para proporcionar operaciones CRUD
 * básicas y consultas personalizadas para la entidad Genera_recuso.
 */
public interface GeneraRecursoRepository extends JpaRepository<Genera_recuso, Long> {
    /**
     * Busca todos los generadores de recursos asociados a una ciudad específica.
     *
     * @param ciudad La ciudad para la cual se desean obtener los generadores de recursos.
     * @return Una lista de generadores de recursos (Genera_recuso) asociados a la ciudad proporcionada.
     */
    List<Genera_recuso> findByCiudad(Ciudad ciudad);
}
