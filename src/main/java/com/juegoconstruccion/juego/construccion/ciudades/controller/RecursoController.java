package com.juegoconstruccion.juego.construccion.ciudades.controller;

import com.juegoconstruccion.juego.construccion.ciudades.dto.RecursoDto;
import com.juegoconstruccion.juego.construccion.ciudades.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los recursos en la aplicación.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar recursos.
 */
@RestController
@RequestMapping("/api/recursos")
public class RecursoController {
    private final RecursoService recursoService;

    /**
     * Constructor del controlador que inyecta el servicio de recursos.
     *
     * @param recursoService Servicio de recursos.
     */
    @Autowired
    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    /**
     * Obtiene una lista de todos los recursos.
     *
     * @return ResponseEntity con una lista de RecursoDto y el estado HTTP 200 OK.
     */
    @GetMapping("/all")
    public ResponseEntity<List<RecursoDto>> obtenerTodosLosRecursos() {
        List<RecursoDto> recursos = recursoService.obtenerTodosLosRecursos();
        return ResponseEntity.ok(recursos);
    }

    /**
     * Obtiene un recurso por su ID.
     *
     * @param id ID del recurso a obtener.
     * @return ResponseEntity con el RecursoDto correspondiente y el estado HTTP 200 OK.
     */
    @GetMapping("/obtener/{id}")
    public ResponseEntity<RecursoDto> obtenerRecursoPorId(@PathVariable Long id) {
        RecursoDto recurso = recursoService.obtenerRecursoPorId(id);
        return ResponseEntity.ok(recurso);
    }

    /**
     * Crea un nuevo recurso.
     *
     * @param recursoDto Objeto que contiene la información del recurso a crear.
     * @return ResponseEntity con el nuevo RecursoDto y el estado HTTP 201 Created.
     */
    @PostMapping("/crear")
    public ResponseEntity<RecursoDto> crearRecurso(@RequestBody RecursoDto recursoDto) {
        RecursoDto nuevoRecurso = recursoService.crearRecurso(recursoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRecurso);
    }

    /**
     * Actualiza un recurso existente.
     *
     * @param id ID del recurso a actualizar.
     * @param recursoDto Objeto que contiene la nueva información del recurso.
     * @return ResponseEntity con el RecursoDto actualizado y el estado HTTP 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecursoDto> actualizarRecurso(@PathVariable Long id, @RequestBody RecursoDto recursoDto) {
        RecursoDto recursoActualizado = recursoService.actualizarRecurso(id, recursoDto);
        return ResponseEntity.ok(recursoActualizado);
    }

    /**
     * Elimina un recurso por su ID.
     *
     * @param id ID del recurso a eliminar.
     * @return ResponseEntity con estado HTTP 204 No Content si la eliminación es exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminarRecurso(id);
        return ResponseEntity.noContent().build();
    }
}
