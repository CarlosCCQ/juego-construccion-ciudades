package com.build.api.service.edificioservice;


import com.build.api.dto.EdificioDto;
import com.build.api.model.recurso.Tipo_recurso;

import java.util.List;
import java.util.Map;

public interface IEdificioService {
    List<EdificioDto> obtenerTodosLosEdificios();
    EdificioDto obtenerEdificioPorId(Long id);
    EdificioDto crearEdificio(EdificioDto edificioDto);
    EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto);
    void eliminarEdificio(Long id);
    boolean verificarRecursosParaConstruir(Long ciudadId, Map<Tipo_recurso, Integer> costo);
    boolean mejorarEdificio(Long edificioId);
}
