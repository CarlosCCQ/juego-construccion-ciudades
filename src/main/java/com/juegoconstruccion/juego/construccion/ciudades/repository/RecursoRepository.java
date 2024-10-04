package com.juegoconstruccion.juego.construccion.ciudades.repository;

import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.model.Recurso;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz que define los métodos para interactuar con la base de datos
 * relacionada con los recursos.
 *
 * Extiende JpaRepository para proporcionar operaciones CRUD
 * básicas y consultas personalizadas para la entidad Recurso.
 */
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
    /**
     * Busca todos los recursos de un tipo específico que pertenecen a una ciudad específica.
     *
     * @param tipoRecursos El tipo de recurso que se desea buscar.
     * @param ciudad La ciudad para la cual se desean obtener los recursos.
     * @return Una lista de recursos (Recurso) que coinciden con el tipo y la ciudad proporcionados.
     */
    List<Recurso> findByTipoRecursosAndCiudad(Tipo_recurso tipoRecursos, Ciudad ciudad);

    /**
     * Busca todos los recursos de un tipo específico sin filtrar por ciudad.
     *
     * @param tipoRecursos El tipo de recurso que se desea buscar.
     * @return Una lista de recursos (Recurso) que coinciden con el tipo proporcionado.
     */
    List<Recurso> findByTipoRecursos(Tipo_recurso tipoRecursos);
}
