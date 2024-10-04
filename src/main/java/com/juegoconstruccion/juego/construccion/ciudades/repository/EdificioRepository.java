package com.juegoconstruccion.juego.construccion.ciudades.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juegoconstruccion.juego.construccion.ciudades.model.edificio.Edificio;

/**
 * Repositorio para la entidad Edificio.
 * 
 * Esta interfaz extiende JpaRepository, lo que permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre la entidad Edificio sin necesidad de implementar los métodos manualmente.
 * 
 * JpaRepository proporciona métodos predeterminados como:
 * 
 * - save(): Guardar o actualizar una entidad.
 * - findById(): Buscar una entidad por su ID.
 * - findAll(): Obtener todas las entidades.
 * - delete(): Eliminar una entidad.
 * 
 * @param <Edificio> El tipo de la entidad sobre la que se trabajará.
 * @param <Long> El tipo del identificador (ID) de la entidad.
 */
public interface EdificioRepository extends JpaRepository<Edificio, Long> {
    // Los métodos CRUD básicos ya están proporcionados por JpaRepository.
}
