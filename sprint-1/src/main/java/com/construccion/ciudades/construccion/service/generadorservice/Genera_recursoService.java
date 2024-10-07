package com.construccion.ciudades.construccion.service.generadorservice;

import com.construccion.ciudades.construccion.dto.Genera_recursoDto;
import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import com.construccion.ciudades.construccion.model.generador.Genera_recuso;
import com.construccion.ciudades.construccion.model.recurso.Recurso;
import com.construccion.ciudades.construccion.repository.CiudadRepository;
import com.construccion.ciudades.construccion.repository.GeneraRecursoRepository;
import com.construccion.ciudades.construccion.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Genera_recursoService implements IGenera_recursoService{
    private final GeneraRecursoRepository generaRecursoRepository;
    private final CiudadRepository ciudadRepository;
    private final RecursoRepository recursoRepository;

    @Autowired
    public Genera_recursoService(GeneraRecursoRepository generaRecursoRepository,
                                 CiudadRepository ciudadRepository,
                                 RecursoRepository recursoRepository) {
        this.generaRecursoRepository = generaRecursoRepository;
        this.ciudadRepository = ciudadRepository;
        this.recursoRepository = recursoRepository;
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

    @Override
    public void eliminarGenerador(Long id) {
        generaRecursoRepository.deleteById(id);
    }
    private Genera_recursoDto convertirAGeneraRecursoDto(Genera_recuso generador) {
        return new Genera_recursoDto(
                generador.getTipoGeneradorRecurso(),
                generador.getCiudad().getId(),
                generador.getRecursoGenerado().getTipoRecursos()
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
}
