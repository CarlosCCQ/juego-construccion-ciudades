package com.juegoconstruccion.juego.construccion.ciudades.service;

import com.juegoconstruccion.juego.construccion.ciudades.dto.CiudadDto;
import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;

import java.util.List;

public interface ICiudadService {
    List<CiudadDto> obtenerTodasLasCiudades();
    CiudadDto obtenerCiudadPorId(Long id);
    CiudadDto crearCiudad(CiudadDto ciudadDto);
    CiudadDto actualizarCiudad(Long id, CiudadDto ciudadDto);
    void eliminarCiudad(Long id);
    void agregarRecursoAlaCiudad(Long ciudadId, Long recursoId);
    void agregarGeneradorAlaCiudad(Long ciudadId, Long generaRecursoId);
    void agregarEdificioAlaCiudad(Long ciudadId, Long edificioId);
}