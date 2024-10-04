package com.juegoconstruccion.juego.construccion.ciudades.repository;

import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * La interfaz CiudadRepository extiende JpaRepository y proporciona
 * una serie de métodos para interactuar con la base de datos en lo que
 * respecta a la entidad Ciudad. Es un componente de la arquitectura Spring Data JPA.
 *
 * Este repositorio permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre la entidad Ciudad sin necesidad de implementar métodos de acceso a datos explícitos.
 */
@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    /**
     * Encuentra todas las ciudades cuyo nombre coincide con el parámetro dado.
     *
     * @param nombreCiudad El nombre de la ciudad que se busca.
     * @return Una lista de ciudades que tienen el nombre especificado.
     */
    List<Ciudad> findByNombre(String nombreCiudad);
}
