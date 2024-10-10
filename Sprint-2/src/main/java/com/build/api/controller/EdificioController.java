package com.build.api.controller;


import com.build.api.dto.EdificioDto;
import com.build.api.service.edificioservice.IEdificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edificios")
public class EdificioController {
    @Autowired
    private IEdificioService edificioService;

    @GetMapping("/todos")
    public List<EdificioDto> obtenerTodosLosEdificios() {
        return edificioService.obtenerTodosLosEdificios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EdificioDto> obtenerEdificioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(edificioService.obtenerEdificioPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<EdificioDto> crearEdificio(@RequestBody EdificioDto edificioDto) {
        return new ResponseEntity<>(edificioService.crearEdificio(edificioDto), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EdificioDto> actualizarEdificio(@PathVariable Long id, @RequestBody EdificioDto edificioDto) {
        return ResponseEntity.ok(edificioService.actualizarEdificio(id, edificioDto));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEdificio(@PathVariable Long id) {
        edificioService.eliminarEdificio(id);
        return ResponseEntity.noContent().build();
    }
}
