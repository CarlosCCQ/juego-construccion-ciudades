package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.RecursoDto;

import java.util.List;


/**
 * Interfaz que define los servicios relacionados con la gestión de recursos.
 * Proporciona métodos para realizar operaciones CRUD sobre recursos asociados a ciudades.
 */
public interface IRecursoService {

    /**
     * Obtiene una lista de todos los recursos.
     *
     * @return una lista de objetos RecursoDto que representan todos los recursos en el sistema.
     */
    List<RecursoDto> obtenerTodosLosRecursos();

    /**
     * Obtiene un recurso específico por su ID.
     *
     * @param id el ID del recurso que se desea obtener.
     * @return un objeto RecursoDto que representa el recurso correspondiente al ID proporcionado.
     * @throws RuntimeException si el recurso no se encuentra.
     */
    RecursoDto obtenerRecursoPorId(Long id);

    /**
     * Crea un nuevo recurso en el sistema.
     *
     * @param recursoDto el objeto RecursoDto que contiene los datos del recurso a crear.
     * @return el objeto RecursoDto del recurso creado.
     */
    RecursoDto crearRecurso(RecursoDto recursoDto);

    /**
     * Actualiza un recurso existente en el sistema.
     *
     * @param id el ID del recurso que se desea actualizar.
     * @param recursoDto el objeto RecursoDto que contiene los datos actualizados del recurso.
     * @return el objeto RecursoDto del recurso actualizado.
     * @throws RuntimeException si el recurso no se encuentra.
     */
    RecursoDto actualizarRecurso(Long id, RecursoDto recursoDto);

    /**
     * Elimina un recurso del sistema.
     *
     * @param id el ID del recurso que se desea eliminar.
     * @throws RuntimeException si el recurso no se encuentra.
     */
    void eliminarRecurso(Long id);
}
