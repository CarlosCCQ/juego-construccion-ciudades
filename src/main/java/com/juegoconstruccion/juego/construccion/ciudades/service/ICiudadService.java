package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.CiudadDto;

import java.util.List;

/**
 * La interfaz ICiudadService define los métodos de servicio que deben ser implementados
 * para gestionar la lógica de negocio relacionada con las ciudades.
 *
 * Esta interfaz actúa como un contrato que especifica las operaciones disponibles
 * para manejar entidades Ciudad, proporcionando métodos para operaciones CRUD
 * y otras acciones específicas como la adición de recursos, generadores y edificios.
 */
public interface ICiudadService {

    /**
     * Obtiene todas las ciudades desde la base de datos.
     *
     * @return Una lista de objetos CiudadDto que representan todas las ciudades.
     */
    List<CiudadDto> obtenerTodasLasCiudades();

    /**
     * Obtiene una ciudad por su ID.
     *
     * @param id El ID de la ciudad que se desea obtener.
     * @return Un objeto CiudadDto que contiene los datos de la ciudad solicitada.
     */
    CiudadDto obtenerCiudadPorId(Long id);

    /**
     * Crea una nueva ciudad.
     *
     * @param ciudadDto Los datos de la ciudad que se desea crear, empaquetados en un DTO.
     * @return Un objeto CiudadDto con los datos de la ciudad recién creada.
     */
    CiudadDto crearCiudad(CiudadDto ciudadDto);

    /**
     * Actualiza una ciudad existente.
     *
     * @param id El ID de la ciudad que se desea actualizar.
     * @param ciudadDto Los nuevos datos para la ciudad, empaquetados en un DTO.
     * @return Un objeto CiudadDto con los datos de la ciudad actualizada.
     */
    CiudadDto actualizarCiudad(Long id, CiudadDto ciudadDto);

    /**
     * Elimina una ciudad por su ID.
     *
     * @param id El ID de la ciudad que se desea eliminar.
     */
    void eliminarCiudad(Long id);

    /**
     * Agrega un recurso existente a una ciudad.
     *
     * @param ciudadId El ID de la ciudad a la que se añadirá el recurso.
     * @param recursoId El ID del recurso que se añadirá a la ciudad.
     */
    void agregarRecursoAlaCiudad(Long ciudadId, Long recursoId);

    /**
     * Agrega un generador de recursos existente a una ciudad.
     *
     * @param ciudadId El ID de la ciudad a la que se añadirá el generador de recursos.
     * @param generaRecursoId El ID del generador de recursos que se añadirá a la ciudad.
     */
    void agregarGeneradorAlaCiudad(Long ciudadId, Long generaRecursoId);

    /**
     * Agrega un edificio existente a una ciudad.
     *
     * @param ciudadId El ID de la ciudad a la que se añadirá el edificio.
     * @param edificioId El ID del edificio que se añadirá a la ciudad.
     */
    void agregarEdificioAlaCiudad(Long ciudadId, Long edificioId);
}
