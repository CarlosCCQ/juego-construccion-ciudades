package com.build.api.service.recursoservice;

import com.build.api.dto.RecursoDto;
import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.generador.Genera_recuso;
import com.build.api.model.recurso.Recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.repository.CiudadRepository;
import com.build.api.repository.GeneraRecursoRepository;
import com.build.api.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecursoService implements IRecursoService{
    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    private final GeneraRecursoRepository generaRecursoRepository;

    @Autowired
    public RecursoService(GeneraRecursoRepository generaRecursoRepository) {
        this.generaRecursoRepository = generaRecursoRepository;
    }

    @Override
    public List<RecursoDto> obtenerTodosLosRecursos() {
        return recursoRepository.findAll().stream()
                .map(this::convertirARecursoDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecursoDto obtenerRecursoPorId(Long id) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return convertirARecursoDto(recurso);
    }

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

    @Override
    public RecursoDto crearRecurso(RecursoDto recursoDto) {
        if (recursoDto.getCiudadId() == null) {
            throw new IllegalArgumentException("El ID de la ciudad no debe ser nulo");
        }
        Ciudad ciudad = ciudadRepository.findById(recursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        if (recursoDto.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad del recurso debe ser mayor que cero.");
        }

        Recurso recurso = new Recurso();
        recurso.setTipoRecursos(recursoDto.getTipoRecursos());
        recurso.setCantidad(recursoDto.getCantidad());
        recurso.setCiudad(ciudad);

        return convertirARecursoDto(recursoRepository.save(recurso));
    }

    @Override
    public RecursoDto actualizarRecurso(Long id, RecursoDto recursoDto) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado"));

        recurso.setTipoRecursos(recursoDto.getTipoRecursos());
        recurso.setCantidad(recursoDto.getCantidad());

        return convertirARecursoDto(recursoRepository.save(recurso));
    }

    @Override
    public void eliminarRecurso(Long id) {
        recursoRepository.deleteById(id);
    }

    private RecursoDto convertirARecursoDto(Recurso recurso) {
        return new RecursoDto(
                recurso.getId(),
                recurso.getTipoRecursos(),
                recurso.getCantidad(),
                recurso.getCiudad().getId()
        );
    }

    public boolean verificarSuficientesRecursos(Long ciudadId, Tipo_recurso tipoRecurso, int cantidadRequerida) {
        Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecurso, ciudadRepository.findById(ciudadId)
                        .orElseThrow(() -> new RuntimeException("Ciudad no encontrada")))
                .stream().findFirst().orElse(null);

        return recurso != null && recurso.getCantidad() >= cantidadRequerida;
    }

    public RecursoDto obtenerRecursoPorTipoYCiudad(Tipo_recurso tipoRecurso, Long ciudadId) {
        Recurso recurso = recursoRepository.findByTipoRecursosAndCiudad(tipoRecurso, ciudadRepository.findById(ciudadId)
                        .orElseThrow(() -> new RuntimeException("Ciudad no encontrada")))
                .stream().findFirst().orElse(null);

        return recurso != null ? convertirARecursoDto(recurso) : null;
    }

    public void generarRecursosAutomáticamente(Long ciudadId) {
        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        List<Genera_recuso> generadores = generaRecursoRepository.findByCiudad(ciudad);
        for (Genera_recuso generador : generadores) {
            int cantidadGenerada = (int) (Math.random() * 7) + 2; // Genera entre 2 y 8
            aumentarRecurso(ciudadId, generador.getTipoRecursoGenerado(), cantidadGenerada);
        }
    }
}
