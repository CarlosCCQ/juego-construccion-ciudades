package com.juegoconstruccion.juego.construccion.ciudades.controller;

import com.juegoconstruccion.juego.construccion.ciudades.dto.CiudadDto;
import com.juegoconstruccion.juego.construccion.ciudades.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * El controlador CiudadController maneja las solicitudes HTTP relacionadas con las ciudades.
 *
 * Este controlador expone una serie de endpoints REST que permiten realizar operaciones CRUD
 * sobre la entidad Ciudad, así como agregar recursos, generadores y edificios a una ciudad.
 * Utiliza el servicio CiudadService para delegar la lógica de negocio.
 */
@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    // Métodos para realizar operaciones CRUD

    /**
     * Obtiene una lista de todas las ciudades.
     *
     * @return Una lista de objetos CiudadDto que representan todas las ciudades.
     */
    @GetMapping
    public List<CiudadDto> obtenerTodasLasCiudades() {
        return ciudadService.obtenerTodasLasCiudades();
    }

    /**
     * Obtiene los datos de una ciudad específica a través de su ID.
     *
     * @param id El ID de la ciudad que se desea obtener.
     * @return Un objeto CiudadDto con los datos de la ciudad solicitada.
     */
    @GetMapping("/{id}")
    public CiudadDto obtenerCiudadPorId(@PathVariable Long id) {
        return ciudadService.obtenerCiudadPorId(id);
    }

    /**
     * Crea una nueva ciudad en el sistema.
     *
     * @param ciudadDto Los datos de la nueva ciudad en formato JSON.
     * @return Un objeto CiudadDto con los datos de la ciudad recién creada.
     */
    @PostMapping
    public CiudadDto crearCiudad(@RequestBody CiudadDto ciudadDto) {
        return ciudadService.crearCiudad(ciudadDto);
    }

    /**
     * Actualiza una ciudad existente en el sistema.
     *
     * @param id El ID de la ciudad que se desea actualizar.
     * @param ciudadDto Los nuevos datos de la ciudad en formato JSON.
     * @return Un objeto CiudadDto con los datos actualizados de la ciudad.
     */
    @PutMapping("/{id}")
    public CiudadDto actualizarCiudad(@PathVariable Long id, @RequestBody CiudadDto ciudadDto) {
        return ciudadService.actualizarCiudad(id, ciudadDto);
    }

    /**
     * Elimina una ciudad del sistema a través de su ID.
     *
     * @param id El ID de la ciudad que se desea eliminar.
     */
    @DeleteMapping("/{id}")
    public void eliminarCiudad(@PathVariable Long id) {
        ciudadService.eliminarCiudad(id);
    }

    // Métodos para agregar recursos, generadores y edificios a una ciudad

    /**
     * Agrega un recurso existente a una ciudad específica.
     *
     * @param ciudadId El ID de la ciudad a la que se desea agregar el recurso.
     * @param recursoId El ID del recurso que se desea agregar a la ciudad.
     */
    @PostMapping("/{ciudadId}/recursos/{recursoId}")
    public void agregarRecursoAlaCiudad(@PathVariable Long ciudadId, @PathVariable Long recursoId) {
        ciudadService.agregarRecursoAlaCiudad(ciudadId, recursoId);
    }

    /**
     * Agrega un generador de recursos existente a una ciudad específica.
     *
     * @param ciudadId El ID de la ciudad a la que se desea agregar el generador de recursos.
     * @param generaRecursoId El ID del generador de recursos que se desea agregar a la ciudad.
     */
    @PostMapping("/{ciudadId}/generadores/{generaRecursoId}")
    public void agregarGeneradorAlaCiudad(@PathVariable Long ciudadId, @PathVariable Long generaRecursoId) {
        ciudadService.agregarGeneradorAlaCiudad(ciudadId, generaRecursoId);
    }

    /**
     * Agrega un edificio existente a una ciudad específica.
     *
     * @param ciudadId El ID de la ciudad a la que se desea agregar el edificio.
     * @param edificioId El ID del edificio que se desea agregar a la ciudad.
     */
    @PostMapping("/{ciudadId}/edificios/{edificioId}")
    public void agregarEdificioAlaCiudad(@PathVariable Long ciudadId, @PathVariable Long edificioId) {
        ciudadService.agregarEdificioAlaCiudad(ciudadId, edificioId);
    }
}
