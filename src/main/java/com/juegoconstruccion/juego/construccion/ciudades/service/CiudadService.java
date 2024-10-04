package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.CiudadDto;
import com.juegoconstruccion.juego.construccion.ciudades.model.*;
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

@Service
public class CiudadService implements ICiudadService{

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private GeneraRecursoRepository generaRecursoRepository;

    @Autowired
    private EdificioRepository edificioRepository;

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

    @Override
    public CiudadDto crearCiudad(CiudadDto ciudadDto) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(ciudadDto.getNombre());

        ciudad = ciudadRepository.save(ciudad);

        for (Tipo_recurso tipo : Tipo_recurso.values()) {
            Recurso recurso = new Recurso(tipo, 10, ciudad);
            recursoRepository.save(recurso);
        }

        ciudadRepository.save(ciudad);
        return convertirACiudadDto(ciudad);
    }

    @Override
    public CiudadDto actualizarCiudad(Long id, CiudadDto ciudadDto) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        ciudad.setNombre(ciudadDto.getNombre());
        return convertirACiudadDto(ciudadRepository.save(ciudad));
    }

    @Override
    public void eliminarCiudad(Long id) {
        ciudadRepository.deleteById(id);
    }

    @Override
    public void agregarRecursoAlaCiudad(Long ciudadId, Long recursoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        ciudad.getRecursos().add(recurso);
        ciudadRepository.save(ciudad);
    }

    @Override
    public void agregarGeneradorAlaCiudad(Long ciudadId, Long generaRecursoId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        Genera_recuso generaRecurso = generaRecursoRepository.findById(generaRecursoId)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));
        ciudad.getGeneraRecusos().add(generaRecurso);
        ciudadRepository.save(ciudad);
    }

    @Override
    public void agregarEdificioAlaCiudad(Long ciudadId, Long edificioId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Edificio edificio = edificioRepository.findById(edificioId)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));

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


    private void asignarGeneradorDeRecursos(Ciudad ciudad){
        crearGenerador(ciudad, Tipo_generador_recurso.CANTERAS, Tipo_recurso.PIEDRA);
        crearGenerador(ciudad, Tipo_generador_recurso.MINAS, Tipo_recurso.ORO);
        crearGenerador(ciudad, Tipo_generador_recurso.RIO, Tipo_recurso.AGUA);
    }
    private void crearGenerador(Ciudad ciudad, Tipo_generador_recurso tipoGenerador, Tipo_recurso tipoRecursos) {
        Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecursos, ciudad).stream().findFirst().orElseThrow(()-> new RuntimeException("Recurso no encontrado para ciudad"));

        Genera_recuso generador = new Genera_recuso();
        generador.setTipoGeneradorRecurso(tipoGenerador);
        generador.setCiudad(ciudad);
        generador.setRecursoGenerado(recurso);
        generador.setCapacidadGeneracion(generarCapacidadAleatoria());
        generaRecursoRepository.save(generador);

        generarRecursosPeriodicamente(generador);
    }

    private int generarCapacidadAleatoria() {
        return (int) (Math.random() * 7) + 2;
    }

    private void generarRecursosPeriodicamente(Genera_recuso generador) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            Recurso recurso = generador.getRecursoGenerado();
            recurso.setCantidad(recurso.getCantidad() + generador.getCapacidadGeneracion());
            recursoRepository.save(recurso);

            System.out.println("Generados " + generador.getCapacidadGeneracion() + " unidades de " + recurso.getTipoRecursos() + " en la ciudad " + generador.getCiudad().getNombre());
        }, 0, 10, TimeUnit.SECONDS);
    }

    public Map<Tipo_recurso, Integer> obtenerRecursoDeLaCiudad(String nombreCiudad){
        Ciudad ciudad = ciudadRepository.findByNombre(nombreCiudad).stream().findFirst().orElseThrow(()-> new RuntimeException("Ciudad no encontradad con el nombre: "+ nombreCiudad));

        return ciudad.getRecursos().stream().collect(Collectors.toMap(Recurso::getTipoRecursos, Recurso:: getCantidad));
    }

    public CiudadDto obtenerCiudadPorNombre(String nombreCiudad){
        Ciudad ciudad = ciudadRepository.findByNombre(nombreCiudad).stream().findFirst().orElseThrow(()-> new RuntimeException("Ciudad no encontrada"));

        return convertirACiudadDto(ciudad);
    }
}