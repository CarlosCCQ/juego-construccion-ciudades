package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.Genera_recursoDto;
import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.model.Genera_recuso;
import com.juegoconstruccion.juego.construccion.ciudades.model.Recurso;
import com.juegoconstruccion.juego.construccion.ciudades.repository.CiudadRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.GeneraRecursoRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de servicio que maneja la lógica de negocio relacionada con los generadores de recursos.
 * Implementa la interfaz IGenera_recursoService para proporcionar operaciones CRUD y consultas
 * para la entidad Genera_recuso.
 */
@Service
public class Genera_recursoService implements IGenera_recursoService{

    /**
     * Repositorio para acceder a las operaciones de la entidad Genera_recuso.
     */
    private final GeneraRecursoRepository generaRecursoRepository;

    /**
     * Repositorio para acceder a las operaciones de la entidad Ciudad.
     */
    private final CiudadRepository ciudadRepository;

    /**
     * Repositorio para acceder a las operaciones de la entidad Recurso.
     */
    private final RecursoRepository recursoRepository;

    /**
     * Constructor de la clase Genera_recursoService.
     *
     * @param generaRecursoRepository Repositorio para operaciones con generadores de recursos.
     * @param ciudadRepository        Repositorio para operaciones con ciudades.
     * @param recursoRepository       Repositorio para operaciones con recursos.
     */
    @Autowired
    public Genera_recursoService(GeneraRecursoRepository generaRecursoRepository,
                                 CiudadRepository ciudadRepository,
                                 RecursoRepository recursoRepository) {
        this.generaRecursoRepository = generaRecursoRepository;
        this.ciudadRepository = ciudadRepository;
        this.recursoRepository = recursoRepository;
    }

    /**
     * Obtiene todos los generadores de recursos.
     *
     * @return Una lista de objetos Genera_recursoDto que representan todos los generadores.
     */
    @Override
    public List<Genera_recursoDto> obtenerTodosLosGeneradores() {
        List<Genera_recuso> generadores = generaRecursoRepository.findAll();
        return generadores.stream()
                .map(this::convertirAGeneraRecursoDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un generador de recursos por su ID.
     *
     * @param id El ID del generador que se desea obtener.
     * @return Un objeto Genera_recursoDto que representa el generador encontrado.
     * @throws RuntimeException si el generador no se encuentra.
     */
    @Override
    public Genera_recursoDto obtenerGeneradorPorId(Long id) {
        Genera_recuso generador = generaRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));
        return convertirAGeneraRecursoDto(generador);
    }

    /**
     * Crea un nuevo generador de recursos.
     *
     * @param generaRecursoDto Objeto que contiene los datos del generador a crear.
     * @return Un objeto Genera_recursoDto que representa el generador creado.
     * @throws RuntimeException si la ciudad o el recurso no se encuentran.
     */
    @Override
    public Genera_recursoDto crearGenerador(Genera_recursoDto generaRecursoDto) {
        Ciudad ciudad = ciudadRepository.findById(generaRecursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Recurso recurso = recursoRepository.findByTipoRecursos(generaRecursoDto.getTipoRecursoGenerado())
                .stream().findFirst().orElseThrow(()-> new RuntimeException("Recurso no encontrado"));

        Genera_recuso generador = new Genera_recuso();
        generador.setTipoGeneradorRecurso(generaRecursoDto.getTipoGeneradorRecurso());
        generador.setCiudad(ciudad);
        generador.setRecursoGenerado(recurso);
        generador.setCapacidadGeneracion(generaRecursoDto.getCapacidadGeneracion());

        generaRecursoRepository.save(generador);
        return convertirAGeneraRecursoDto(generador);
    }

    /**
     * Actualiza un generador de recursos existente.
     *
     * @param id El ID del generador que se desea actualizar.
     * @param generaRecursoDto Objeto que contiene los nuevos datos del generador.
     * @return Un objeto Genera_recursoDto que representa el generador actualizado.
     * @throws RuntimeException si el generador, la ciudad o el recurso no se encuentran.
     */
    @Override
    public Genera_recursoDto actualizarGenerador(Long id, Genera_recursoDto generaRecursoDto) {
        Genera_recuso generador = generaRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));

        Ciudad ciudad = ciudadRepository.findById(generaRecursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Recurso recurso = recursoRepository.findByTipoRecursos(generaRecursoDto.getTipoRecursoGenerado())
                .stream().findFirst().orElseThrow(()-> new RuntimeException("Recurso no encontrado"));

        generador.setTipoGeneradorRecurso(generaRecursoDto.getTipoGeneradorRecurso());
        generador.setCiudad(ciudad);
        generador.setRecursoGenerado(recurso);
        generador.setCapacidadGeneracion(generaRecursoDto.getCapacidadGeneracion());

        generaRecursoRepository.save(generador);
        return convertirAGeneraRecursoDto(generador);
    }

    /**
     * Elimina un generador de recursos por su ID.
     *
     * @param id El ID del generador que se desea eliminar.
     */
    @Override
    public void eliminarGenerador(Long id) {
        generaRecursoRepository.deleteById(id);
    }

    /**
     * Convierte un objeto Genera_recuso en un objeto Genera_recursoDto.
     *
     * @param generador El generador a convertir.
     * @return Un objeto Genera_recursoDto que representa el generador.
     */
    private Genera_recursoDto convertirAGeneraRecursoDto(Genera_recuso generador) {
        return new Genera_recursoDto(
                generador.getTipoGeneradorRecurso(),
                generador.getCiudad().getId(),
                generador.getRecursoGenerado().getTipoRecursos()
        );
    }

    /**
     * Obtiene todos los generadores de recursos de una ciudad específica.
     *
     * @param ciudadId El ID de la ciudad de la cual se desean obtener los generadores.
     * @return Una lista de objetos Genera_recursoDto que representan los generadores en la ciudad.
     * @throws RuntimeException si la ciudad no se encuentra.
     */
    public List<Genera_recursoDto> obtenerGeneradoresPorCiudad(Long ciudadId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        List<Genera_recuso> generadores = generaRecursoRepository.findByCiudad(ciudad);

        return generadores.stream()
                .map(this::convertirAGeneraRecursoDto)
                .collect(Collectors.toList());
    }
}
