package com.build.api.service.recursoservice;

import com.build.api.dto.RecursoDto;

import java.util.List;

public interface IRecursoService {
    List<RecursoDto> obtenerTodosLosRecursos();
    RecursoDto obtenerRecursoPorId(Long id);
    RecursoDto crearRecurso(RecursoDto recursoDto);
    RecursoDto actualizarRecurso(Long id, RecursoDto recursoDto);
    void eliminarRecurso(Long id);
}