package com.juegoconstruccion.juego.construccion.ciudades.Sprint1.service.edificio;

import java.util.List;
import java.util.Map;

import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.Tipo_recurso;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.dto.EdificioDto;

public interface IEdificioService {
    List<EdificioDto> obtenerTodosLosEdificios();
    EdificioDto obtenerEdificioPorId(Long id);
    EdificioDto crearEdificio(EdificioDto edificioDto);
    EdificioDto actualizarEdificio(Long id, EdificioDto edificioDto);
    void eliminarEdificio(Long id);
    boolean verificarRecursosParaConstruir(Long ciudadId, Map<Tipo_recurso, Integer> costo);
}
