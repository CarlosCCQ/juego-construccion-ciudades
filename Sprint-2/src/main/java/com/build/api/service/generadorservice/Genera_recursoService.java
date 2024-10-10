package com.build.api.service.generadorservice;

import com.build.api.dto.CiudadDto;
import com.build.api.dto.Genera_recursoDto;
import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.generador.Genera_recuso;
import com.build.api.model.generador.Tipo_generador_recurso;
import com.build.api.model.recurso.Recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.repository.CiudadRepository;
import com.build.api.repository.GeneraRecursoRepository;
import com.build.api.repository.RecursoRepository;
import com.build.api.service.ciudadservice.CiudadService;
import com.build.api.service.recursoservice.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class Genera_recursoService implements IGenera_recursoService {
    private final GeneraRecursoRepository generaRecursoRepository;
    private final CiudadRepository ciudadRepository;
    private final RecursoRepository recursoRepository;
    private final ScheduledExecutorService scheduler;
    private final RecursoService recursoService;

    @Autowired
    @Lazy // Marcamos esta dependencia como Lazy para evitar el ciclo
    private CiudadService ciudadService;
    //private final Genera_recursoService generaRecursoService;

    @Autowired
    public Genera_recursoService(GeneraRecursoRepository generaRecursoRepository,
                                 CiudadRepository ciudadRepository,
                                 RecursoRepository recursoRepository,
                                 @Qualifier("taskScheduler") ScheduledExecutorService scheduler,
                                 RecursoService recursoService,
                                 CiudadService ciudadService) {
        this.generaRecursoRepository = generaRecursoRepository;
        this.ciudadRepository = ciudadRepository;
        this.recursoRepository = recursoRepository;
        this.scheduler = scheduler;
        this.recursoService = recursoService;
        this.ciudadService = ciudadService;
        //this.generaRecursoService = generaRecursoService;
    }

    @Override
    public List<Genera_recursoDto> obtenerTodosLosGeneradores() {
        List<Genera_recuso> generadores = generaRecursoRepository.findAll();
        return generadores.stream()
                .map(this::convertirAGeneraRecursoDto)
                .collect(Collectors.toList());
    }

    @Override
    public Genera_recursoDto obtenerGeneradorPorId(Long id) {
        Genera_recuso generador = generaRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));
        return convertirAGeneraRecursoDto(generador);
    }

    @Override
    public Genera_recursoDto crearGenerador(Ciudad ciudad, Tipo_generador_recurso tipoGenerador, Tipo_recurso tipoRecursos) {
        Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecursos, ciudad)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado para ciudad"));

        Genera_recuso nuevoGenerador = new Genera_recuso();
        nuevoGenerador.setTipoGeneradorRecurso(tipoGenerador);
        nuevoGenerador.setCiudad(ciudad);
        nuevoGenerador.setRecursoGenerado(recurso);
        nuevoGenerador.setCantidadGenerada(generarCapacidadAleatoria());

        generaRecursoRepository.save(nuevoGenerador);

        generarRecursosPeriodicamente(nuevoGenerador);

        return convertirAGeneraRecursoDto(nuevoGenerador);
    }

    public void generarRecursosPeriodicamente(Genera_recuso generador) {
        Runnable generarRecursosTask = () -> {
            Ciudad ciudad = generador.getCiudad();
            int cantidadGenerada = generarCapacidadAleatoria();

            recursoService.aumentarRecurso(ciudad.getId(), generador.getRecursoGenerado().getTipoRecursos(), cantidadGenerada);

            System.out.println("Se generaron " + cantidadGenerada + " unidades de " + generador.getRecursoGenerado().getTipoRecursos()
                    + " en la ciudad " + ciudad.getNombre());
        };

        scheduler.scheduleAtFixedRate(generarRecursosTask, 15, 5, TimeUnit.SECONDS);
    }

    public int generarCapacidadAleatoria() {
        return (int) (Math.random() * 50) + 25;
    }

    @Override
    public Genera_recursoDto actualizarGenerador(Long id, Genera_recursoDto generaRecursoDto) {
        Genera_recuso generador = generaRecursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Generador no encontrado"));

        Ciudad ciudad = ciudadRepository.findById(generaRecursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        Recurso recurso = recursoRepository.findByTipoRecursos(generaRecursoDto.getTipoRecursoGenerado())
                .stream().findFirst().orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        generador.setTipoGeneradorRecurso(generaRecursoDto.getTipoGeneradorRecurso());
        generador.setCiudad(ciudad);
        generador.setRecursoGenerado(recurso);
        generador.setCantidadGenerada(generaRecursoDto.getCantidadGenerada());

        generaRecursoRepository.save(generador);
        return convertirAGeneraRecursoDto(generador);
    }

    @Override
    public void eliminarGenerador(Long id) {
        generaRecursoRepository.deleteById(id);
    }

    private Genera_recursoDto convertirAGeneraRecursoDto(Genera_recuso generador) {
        return new Genera_recursoDto(
                generador.getTipoGeneradorRecurso(),
                generador.getCiudad().getId(),
                generador.getRecursoGenerado().getTipoRecursos(),
                generador.getCantidadGenerada()
        );
    }

    public List<Genera_recursoDto> obtenerGeneradoresPorCiudad(Long ciudadId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        List<Genera_recuso> generadores = generaRecursoRepository.findByCiudad(ciudad);

        return generadores.stream()
                .map(this::convertirAGeneraRecursoDto)
                .collect(Collectors.toList());
    }

    public boolean existeGeneradorPorTipoYCiudad(Ciudad ciudad, Tipo_generador_recurso tipoGenerador) {
        List<Genera_recuso> generadores = generaRecursoRepository.findByCiudad(ciudad);
        return generadores.stream().anyMatch(g -> g.getTipoGeneradorRecurso().equals(tipoGenerador));
    }
}
