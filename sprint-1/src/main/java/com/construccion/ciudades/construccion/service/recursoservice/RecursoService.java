package com.construccion.ciudades.construccion.service.recursoservice;

import com.construccion.ciudades.construccion.dto.RecursoDto;
import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import com.construccion.ciudades.construccion.model.recurso.Recurso;
import com.construccion.ciudades.construccion.model.recurso.Tipo_recurso;
import com.construccion.ciudades.construccion.repository.CiudadRepository;
import com.construccion.ciudades.construccion.repository.RecursoRepository;
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
        Ciudad ciudad = ciudadRepository.findById(recursoDto.getCiudadId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

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
}
