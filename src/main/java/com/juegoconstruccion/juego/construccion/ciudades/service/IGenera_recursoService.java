package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.Genera_recursoDto;

import java.util.List;

/**
 * Interfaz que define los servicios relacionados con la entidad Genera_recuso.
 * Proporciona operaciones CRUD y consultas para los generadores de recursos.
 */
public interface IGenera_recursoService {

    /**
     * Obtiene todos los generadores de recursos.
     *
     * @return una lista de objetos Genera_recursoDto que representan todos los generadores.
     */
    List<Genera_recursoDto> obtenerTodosLosGeneradores();

    /**
     * Obtiene un generador de recursos por su ID.
     *
     * @param id el ID del generador que se desea obtener.
     * @return un objeto Genera_recursoDto que representa el generador encontrado.
     * @throws RuntimeException si no se encuentra el generador con el ID proporcionado.
     */
    Genera_recursoDto obtenerGeneradorPorId(Long id);

    /**
     * Crea un nuevo generador de recursos.
     *
     * @param generaRecursoDto el objeto que contiene los datos del generador a crear.
     * @return un objeto Genera_recursoDto que representa el generador creado.
     */
    Genera_recursoDto crearGenerador(Genera_recursoDto generaRecursoDto);

    /**
     * Actualiza un generador de recursos existente.
     *
     * @param id el ID del generador que se desea actualizar.
     * @param generaRecursoDto el objeto que contiene los nuevos datos del generador.
     * @return un objeto Genera_recursoDto que representa el generador actualizado.
     * @throws RuntimeException si no se encuentra el generador con el ID proporcionado.
     */
    Genera_recursoDto actualizarGenerador(Long id, Genera_recursoDto generaRecursoDto);

    /**
     * Elimina un generador de recursos por su ID.
     *
     * @param id el ID del generador que se desea eliminar.
     */
    void eliminarGenerador(Long id);
}
