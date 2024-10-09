package com.build.api.service.generadorservice;


import com.build.api.dto.Genera_recursoDto;
import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.generador.Tipo_generador_recurso;
import com.build.api.model.recurso.Tipo_recurso;

import java.util.List;

public interface IGenera_recursoService {
    List<Genera_recursoDto> obtenerTodosLosGeneradores();
    Genera_recursoDto obtenerGeneradorPorId(Long id);
    Genera_recursoDto crearGenerador(Ciudad ciudad, Tipo_generador_recurso tipoGeneradorRecurso, Tipo_recurso tipoRecurso);
    Genera_recursoDto actualizarGenerador(Long id, Genera_recursoDto generaRecursoDto);
    void eliminarGenerador(Long id);
    //void crearGenerador(Ciudad ciudad, Tipo_generador_recurso tipoGenerador, Tipo_recurso tipoRecursos);
}
