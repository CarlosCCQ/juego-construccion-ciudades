package com.build.api.service.generadorservice;


import com.build.api.dto.Genera_recursoDto;

import java.util.List;

public interface IGenera_recursoService {
    List<Genera_recursoDto> obtenerTodosLosGeneradores();
    Genera_recursoDto obtenerGeneradorPorId(Long id);
    Genera_recursoDto crearGenerador(Genera_recursoDto generaRecursoDto);
    Genera_recursoDto actualizarGenerador(Long id, Genera_recursoDto generaRecursoDto);
    void eliminarGenerador(Long id);
}
