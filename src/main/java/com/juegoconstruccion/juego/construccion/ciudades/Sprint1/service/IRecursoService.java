package com.juegoconstruccion.juego.construccion.ciudades.Sprint1.service;

import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.dto.RecursoDto;

import java.util.List;

public interface IRecursoService {
    List<RecursoDto> obtenerTodosLosRecursos();
    RecursoDto obtenerRecursoPorId(Long id);
    RecursoDto crearRecurso(RecursoDto recursoDto);
    RecursoDto actualizarRecurso(Long id, RecursoDto recursoDto);
    void eliminarRecurso(Long id);
}
