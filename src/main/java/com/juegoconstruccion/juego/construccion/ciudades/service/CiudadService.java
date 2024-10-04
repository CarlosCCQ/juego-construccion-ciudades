package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.CiudadDto;
import com.juegoconstruccion.juego.construccion.ciudades.model.*;
import com.juegoconstruccion.juego.construccion.ciudades.model.edificio.Edificio;
import com.juegoconstruccion.juego.construccion.ciudades.repository.CiudadRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.EdificioRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.GeneraRecursoRepository;
import com.juegoconstruccion.juego.construccion.ciudades.repository.RecursoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * La clase CiudadService implementa la lógica de negocio relacionada con la entidad Ciudad,
 * incluyendo la creación, actualización, eliminación y otras operaciones complejas sobre la ciudad,
 * recursos, edificios y generadores de recursos.
 *
 * Esta clase utiliza múltiples repositorios para realizar las operaciones de persistencia y
 * la inicialización de colecciones asociadas mediante Hibernate.
 */
@Service
public class CiudadService implements ICiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private GeneraRecursoRepository generaRecursoRepository;

    @Autowired
    private EdificioRepository edificioRepository;

    // Métodos CRUD básicos para Ciudad

    /**
     * Obtiene todas las ciudades de la base de datos, inicializando los recursos, generadores
     * y edificios asociados mediante Hibernate. Luego convierte las entidades Ciudad a DTOs.
     *
     * @return Lista de objetos CiudadDto que contienen datos de todas las ciudades.
     */
    @Override
    @Transactional
    public List<CiudadDto> obtenerTodasLasCiudades() {
        List<Ciudad> ciudades = ciudadRepository.findAll();

        ciudades.forEach(ciudad -> {
            Hibernate.initialize(ciudad.getRecursos());
            Hibernate.initialize(ciudad.getGeneraRecusos());
            Hibernate.initialize(ciudad.getEdificios());
        });

        return ciudades.stream()
                .map(this::convertirACiudadDto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una ciudad por su ID, inicializando sus recursos, generadores y edificios.
     *
     * @param id El ID de la ciudad.
     * @return CiudadDto que contiene los datos de la ciudad encontrada.
     * @throws RuntimeException si la ciudad no es encontrada.
     */
    @Override
    @Transactional
    public CiudadDto obtenerCiudadPorId(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Hibernate.initialize(ciudad.getRecursos());
        Hibernate.initialize(ciudad.getGeneraRecusos());
        Hibernate.initialize(ciudad.getEdificios());

        return convertirACiudadDto(ciudad);
    }

    /**
     * Crea una nueva ciudad a partir de un DTO, y asigna recursos iniciales a la ciudad.
     *
     * @param ciudadDto Los datos de la ciudad a crear.
     * @return El DTO de la ciudad creada.
     */
    @Override
    public CiudadDto crearCiudad(CiudadDto ciudadDto) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(ciudadDto.getNombre());

        ciudad = ciudadRepository.save(ciudad);

        // Asignar recursos iniciales a la ciudad
        for (Tipo_recurso tipo : Tipo_recurso.values()) {
            Recurso recurso = new Recurso(tipo, 10, ciudad);
            recursoRepository.save(recurso);
        }

        ciudadRepository.save(ciudad);
        return convertirACiudadDto(ciudad);
    }

    /**
     * Actualiza una ciudad existente con los datos proporcionados en el DTO.
     *
     * @param id El ID de la ciudad a actualizar.
     * @param ciudadDto Los nuevos datos para actualizar la ciudad.
     * @return CiudadDto con los datos de la ciudad actualizada.
     * @throws RuntimeException si la ciudad no es encontrada.
     */
    @Override
    public CiudadDto actualizarCiudad(Long id, CiudadDto ciudadDto) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        ciudad.setNombre(ciudadDto.getNombre());
        return convertirACiudadDto(ciudadRepository.save(ciudad));
    }

    /**
     * Elimina una ciudad por su ID.
     *
     * @param id El ID de la ciudad a eliminar.
     */
    @Override
    public void eliminarCiudad(Long id) {
        ciudadRepository.deleteById(id);
    }

    // Métodos relacionados con recursos, generadores y edificios en la ciudad

    /**
     * Agrega un recurso existente a la ciudad.
     *
     * @param ciudadId El ID de la ciudad.
     * @param recursoId El ID del recurso a agregar.
     */
    @Override
    public void agregarRecursoAlaCiudad(Long ciudadId, Long recursoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        ciudad.getRecursos().add(recurso);
        ciudadRepository.save(ciudad);
    }

    /**
     * Agrega un generador de recursos a la ciudad.
     *
     * @param ciudadId El ID de la ciudad.
     * @param generaRecursoId El ID del generador a agregar.
     */
    @Override
    public void agregarGeneradorAlaCiudad(Long ciudadId, Long generaRecursoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Genera_recuso generaRecurso = generaRecursoRepository.findById(generaRecursoId)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));
        ciudad.getGeneraRecusos().add(generaRecurso);
        ciudadRepository.save(ciudad);
    }

    /**
     * Agrega un edificio a la ciudad, verificando que haya suficientes recursos para su construcción.
     *
     * @param ciudadId El ID de la ciudad.
     * @param edificioId El ID del edificio a agregar.
     * @throws RuntimeException si no hay suficientes recursos para construir el edificio.
     */
    @Override
    public void agregarEdificioAlaCiudad(Long ciudadId, Long edificioId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Edificio edificio = edificioRepository.findById(edificioId)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));

        // Verificar si hay suficientes recursos para construir el edificio
        Map<Tipo_recurso, Integer> costoEdificio = edificio.getCosto();
        for (Map.Entry<Tipo_recurso, Integer> entry : costoEdificio.entrySet()) {
            Tipo_recurso tipoRecursos = entry.getKey();
            int cantidadNecesaria = entry.getValue();

            Recurso recursoCiudad = ciudad.getRecursos().stream()
                    .filter(recurso -> recurso.getTipoRecursos() == tipoRecursos)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No hay suficiente recurso de tipo: " + tipoRecursos));

            if (recursoCiudad.getCantidad() < cantidadNecesaria) {
                throw new RuntimeException("No hay suficientes recursos para construir el edificio");
            }

            recursoCiudad.setCantidad(recursoCiudad.getCantidad() - cantidadNecesaria);
        }

        edificio.setConstruccionCompleta(true);
        edificio.setCiudad(ciudad);

        ciudad.getEdificios().add(edificio);
        ciudadRepository.save(ciudad);

        System.out.println("Edificio construido con éxito: " + edificio.getNombre());
    }

    // Métodos auxiliares

    /**
     * Convierte una entidad Ciudad a un DTO de Ciudad.
     *
     * @param ciudad La entidad Ciudad.
     * @return Un objeto CiudadDto con los datos convertidos.
     */
    private CiudadDto convertirACiudadDto(Ciudad ciudad) {
        System.out.println("Convirtiendo ciudad: " + ciudad.getNombre());
        System.out.println("Recursos: " + ciudad.getRecursos());
        System.out.println("Generadores: " + ciudad.getGeneraRecusos());
        System.out.println("Edificios: " + ciudad.getEdificios());

        return new CiudadDto(
                ciudad.getId(),
                ciudad.getNombre(),
                ciudad.getRecursos() != null ? ciudad.getRecursos().stream().map(Recurso::getId).collect(Collectors.toList()) : new ArrayList<>(),
                ciudad.getGeneraRecusos() != null ? ciudad.getGeneraRecusos().stream().map(Genera_recuso::getId).collect(Collectors.toList()) : new ArrayList<>(),
                ciudad.getEdificios() != null ? ciudad.getEdificios().stream().map(Edificio::getId).collect(Collectors.toList()) : new ArrayList<>()
        );
    }

    // Métodos relacionados con los generadores de recursos

    /**
     * Asigna generadores de recursos iniciales a una ciudad.
     *
     * @param ciudad La ciudad a la que se le asignarán los generadores.
     */
    private void asignarGeneradorDeRecursos(Ciudad ciudad) {
        crearGenerador(ciudad, Tipo_generador_recurso.CANTERAS, Tipo_recurso.PIEDRA);
        crearGenerador(ciudad, Tipo_generador_recurso.MINAS, Tipo_recurso.ORO);
        crearGenerador(ciudad, Tipo_generador_recurso.RIO, Tipo_recurso.AGUA);
    }

    /**
     * Crea un generador de recursos para la ciudad y lo guarda en la base de datos.
     *
     * @param ciudad La ciudad donde se creará el generador.
     * @param tipoGenerador El tipo de generador de recursos.
     * @param tipoRecursos El tipo de recurso que generará.
     */
    private void crearGenerador(Ciudad ciudad, Tipo_generador_recurso tipoGenerador, Tipo_recurso tipoRecursos) {
        Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecursos, ciudad)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado para ciudad"));

        Genera_recuso generador = new Genera_recuso();
        generador.setTipoGeneradorRecurso(tipoGenerador);
        generador.setCiudad(ciudad);
        generador.setRecursoGenerado(recurso);
        generador.setCapacidadGeneracion(generarCapacidadAleatoria());
        generaRecursoRepository.save(generador);

        generarRecursosPeriodicamente(generador);
    }

    /**
     * Genera una capacidad de producción de recursos de manera aleatoria para el generador.
     *
     * @return Un número entero aleatorio que representa la capacidad de generación.
     */
    private int generarCapacidadAleatoria() {
        return (int) (Math.random() * 7) + 2;
    }

    /**
     * Programa la generación periódica de recursos para el generador.
     *
     * @param generador El generador que producirá los recursos.
     */
    private void generarRecursosPeriodicamente(Genera_recuso generador) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            Recurso recurso = generador.getRecursoGenerado();
            recurso.setCantidad(recurso.getCantidad() + generador.getCapacidadGeneracion());
            recursoRepository.save(recurso);

            System.out.println("Generados " + generador.getCapacidadGeneracion() + " unidades de " + recurso.getTipoRecursos() + " en la ciudad " + generador.getCiudad().getNombre());
        }, 0, 10, TimeUnit.SECONDS);
    }

    // Métodos adicionales

    /**
     * Obtiene el inventario de recursos de una ciudad por su nombre.
     *
     * @param nombreCiudad El nombre de la ciudad.
     * @return Un mapa de los tipos de recursos y sus cantidades en la ciudad.
     */
    public Map<Tipo_recurso, Integer> obtenerRecursoDeLaCiudad(String nombreCiudad) {
        Ciudad ciudad = ciudadRepository.findByNombre(nombreCiudad)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con el nombre: " + nombreCiudad));

        return ciudad.getRecursos().stream().collect(Collectors.toMap(Recurso::getTipoRecursos, Recurso::getCantidad));
    }

    /**
     * Obtiene una ciudad por su nombre y devuelve sus datos en forma de DTO.
     *
     * @param nombreCiudad El nombre de la ciudad.
     * @return CiudadDto que contiene los datos de la ciudad.
     */
    public CiudadDto obtenerCiudadPorNombre(String nombreCiudad) {
        Ciudad ciudad = ciudadRepository.findByNombre(nombreCiudad)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        return convertirACiudadDto(ciudad);
    }
}
