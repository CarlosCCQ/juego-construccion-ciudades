package com.construccion.ciudades.construccion.service.edificioservice;

import com.construccion.ciudades.construccion.dto.EdificioDto;
import com.construccion.ciudades.construccion.model.recurso.Tipo_recurso;

import java.util.List;
import java.util.Map;

public interface IEdificioService {
    List<EdificioDto> obtenerTodosLosEdificios();
    EdificioDto obtenerEdificioPorId(Long id);
    EdificioDto crearEdificio(EdificioDto edificioDto);
    EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto);
    void eliminarEdificio(Long id);
    boolean verificarRecursosParaConstruir(Long ciudadId, Map<Tipo_recurso, Integer> costo);
}
