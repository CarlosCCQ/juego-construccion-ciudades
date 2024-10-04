package com.juegoconstruccion.juego.construccion.ciudades.controller;

import com.juegoconstruccion.juego.construccion.ciudades.dto.Genera_recursoDto;
import com.juegoconstruccion.juego.construccion.ciudades.service.Genera_recursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los generadores de recursos en la aplicación.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar generadores de recursos.
 */
@RestController
@RequestMapping("/api/genera-recursos")
public class Genera_recursoController {
    private final Genera_recursoService generaRecursoService;

    /**
     * Constructor del controlador que inyecta el servicio de generadores de recursos.
     *
     * @param generaRecursoService Servicio de generadores de recursos.
     */
    @Autowired
    public Genera_recursoController(Genera_recursoService generaRecursoService) {
        this.generaRecursoService = generaRecursoService;
    }

    /**
     * Obtiene una lista de todos los generadores de recursos.
     *
     * @return ResponseEntity con una lista de Genera_recursoDto y el estado HTTP 200 OK.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Genera_recursoDto>> obtenerTodosLosGeneradores() {
        List<Genera_recursoDto> generadores = generaRecursoService.obtenerTodosLosGeneradores();
        return ResponseEntity.ok(generadores);
    }

    /**
     * Obtiene un generador de recursos por su ID.
     *
     * @param id ID del generador de recursos a obtener.
     * @return ResponseEntity con el Genera_recursoDto correspondiente y el estado HTTP 200 OK.
     */
    @GetMapping("/obtener/{id}")
    public ResponseEntity<Genera_recursoDto> obtenerGeneradorPorId(@PathVariable Long id) {
        Genera_recursoDto generador = generaRecursoService.obtenerGeneradorPorId(id);
        return ResponseEntity.ok(generador);
    }

    /**
     * Crea un nuevo generador de recursos.
     *
     * @param generaRecursoDto Objeto que contiene la información del generador de recursos a crear.
     * @return ResponseEntity con el nuevo Genera_recursoDto y el estado HTTP 201 Created.
     */
    @PostMapping("/crear")
    public ResponseEntity<Genera_recursoDto> crearGenerador(@RequestBody Genera_recursoDto generaRecursoDto) {
        Genera_recursoDto nuevoGenerador = generaRecursoService.crearGenerador(generaRecursoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoGenerador);
    }

    /**
     * Actualiza un generador de recursos existente.
     *
     * @param id ID del generador de recursos a actualizar.
     * @param generaRecursoDto Objeto que contiene la nueva información del generador de recursos.
     * @return ResponseEntity con el Genera_recursoDto actualizado y el estado HTTP 200 OK.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Genera_recursoDto> actualizarGenerador(@PathVariable Long id, @RequestBody Genera_recursoDto generaRecursoDto) {
        Genera_recursoDto generadorActualizado = generaRecursoService.actualizarGenerador(id, generaRecursoDto);
        return ResponseEntity.ok(generadorActualizado);
    }

    /**
     * Elimina un generador de recursos por su ID.
     *
     * @param id ID del generador de recursos a eliminar.
     * @return ResponseEntity con estado HTTP 204 No Content si la eliminación es exitosa.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarGenerador(@PathVariable Long id) {
        generaRecursoService.eliminarGenerador(id);
        return ResponseEntity.noContent().build();
    }
}
