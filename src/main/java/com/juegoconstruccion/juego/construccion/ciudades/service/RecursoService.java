package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.RecursoDto;
import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.model.Recurso;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;
import com.juegoconstruccion.juego.construccion.ciudades.repository.CiudadRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de IRecursoService que proporciona operaciones relacionadas con recursos.
 * Incluye métodos para gestionar recursos en una ciudad, como creación, actualización,
 * eliminación y consulta de recursos.
 */
@Service
public class RecursoService implements IRecursoService{

    /**
     * Repositorio para acceder y gestionar datos de recursos en la base de datos.
     */
    @Autowired
    private RecursoRepository recursoRepository;

    /**
     * Repositorio para acceder y gestionar datos de ciudades en la base de datos.
     */
    @Autowired
    private CiudadRepository ciudadRepository;

    /**
     * Obtiene todos los recursos existentes.
     *
     * @return una lista de objetos RecursoDto que representan todos los recursos.
     */
    @Override
    public List<RecursoDto> obtenerTodosLosRecursos() {
        return recursoRepository.findAll().stream()
                .map(this::convertirARecursoDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un recurso por su ID.
     *
     * @param id el ID del recurso que se desea obtener.
     * @return un objeto RecursoDto que representa el recurso encontrado.
     * @throws RuntimeException si no se encuentra el recurso con el ID proporcionado.
     */
    @Override
    public RecursoDto obtenerRecursoPorId(Long id) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return convertirARecursoDto(recurso);
    }

    /**
     * Aumenta la cantidad de un recurso específico en una ciudad.
     *
     * @param ciudadId       el ID de la ciudad donde se desea aumentar el recurso.
     * @param tipoRecurso    el tipo de recurso que se desea aumentar.
     * @param cantidadAumentar la cantidad a aumentar.
     * @throws RuntimeException si no se encuentra la ciudad con el ID proporcionado.
     */
    public void aumentarRecurso(Long ciudadId, Tipo_recurso tipoRecurso, int cantidadAumentar) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecurso, ciudad)
                .stream().findFirst().orElseGet(() -> {

                    Recurso nuevoRecurso = new Recurso();
                    nuevoRecurso.setTipoRecursos(tipoRecurso);
                    nuevoRecurso.setCiudad(ciudad);
                    nuevoRecurso.setCantidad(0);
                    return nuevoRecurso;
                });

        recurso.setCantidad(recurso.getCantidad() + cantidadAumentar);

        recursoRepository.save(recurso);

        System.out.println("Se han añadido " + cantidadAumentar + " unidades de " + tipoRecurso + " a la ciudad " + ciudad.getNombre());
    }

    /**
     * Crea un nuevo recurso en una ciudad.
     *
     * @param recursoDto el objeto que contiene los datos del recurso a crear.
     * @return un objeto RecursoDto que representa el recurso creado.
     * @throws RuntimeException si no se encuentra la ciudad con el ID proporcionado.
     */
    @Override
    public RecursoDto crearRecurso(RecursoDto recursoDto) {
        Ciudad ciudad = ciudadRepository.findById(recursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Recurso recurso = new Recurso();
        recurso.setTipoRecursos(recursoDto.getTipoRecursos());
        recurso.setCantidad(recursoDto.getCantidad());
        recurso.setCiudad(ciudad);

        return convertirARecursoDto(recursoRepository.save(recurso));
    }

    /**
     * Actualiza un recurso existente.
     *
     * @param id el ID del recurso que se desea actualizar.
     * @param recursoDto el objeto que contiene los nuevos datos del recurso.
     * @return un objeto RecursoDto que representa el recurso actualizado.
     * @throws RuntimeException si no se encuentra el recurso con el ID proporcionado.
     */
    @Override
    public RecursoDto actualizarRecurso(Long id, RecursoDto recursoDto) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        recurso.setTipoRecursos(recursoDto.getTipoRecursos());
        recurso.setCantidad(recursoDto.getCantidad());

        return convertirARecursoDto(recursoRepository.save(recurso));
    }

    /**
     * Elimina un recurso por su ID.
     *
     * @param id el ID del recurso que se desea eliminar.
     */
    @Override
    public void eliminarRecurso(Long id) {
        recursoRepository.deleteById(id);
    }

    /**
     * Convierte un objeto Recurso en un objeto RecursoDto.
     *
     * @param recurso el recurso a convertir.
     * @return un objeto RecursoDto que representa el recurso.
     */
    private RecursoDto convertirARecursoDto(Recurso recurso) {
        return new RecursoDto(
                recurso.getId(),
                recurso.getTipoRecursos(),
                recurso.getCantidad(),
                recurso.getCiudad().getId()
        );
    }
}
