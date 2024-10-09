package com.build.api.controller;

import com.build.api.dto.RecursoDto;
import com.build.api.service.recursoservice.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {
    private final RecursoService recursoService;

    @Autowired
    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @GetMapping
    public ResponseEntity<List<RecursoDto>> obtenerTodosLosRecursos() {
        List<RecursoDto> recursos = recursoService.obtenerTodosLosRecursos();
        return ResponseEntity.ok(recursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoDto> obtenerRecursoPorId(@PathVariable Long id) {
        RecursoDto recurso = recursoService.obtenerRecursoPorId(id);
        return ResponseEntity.ok(recurso);
    }

    @PostMapping
    public ResponseEntity<RecursoDto> crearRecurso(@RequestBody RecursoDto recursoDto) {
        RecursoDto nuevoRecurso = recursoService.crearRecurso(recursoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRecurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoDto> actualizarRecurso(@PathVariable Long id, @RequestBody RecursoDto recursoDto) {
        RecursoDto recursoActualizado = recursoService.actualizarRecurso(id, recursoDto);
        return ResponseEntity.ok(recursoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminarRecurso(id);
        return ResponseEntity.noContent().build();
    }
}
