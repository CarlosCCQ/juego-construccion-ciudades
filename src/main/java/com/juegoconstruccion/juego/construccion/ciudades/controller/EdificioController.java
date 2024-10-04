package com.juegoconstruccion.juego.construccion.ciudades.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juegoconstruccion.juego.construccion.ciudades.dto.EdificioDto;
import com.juegoconstruccion.juego.construccion.ciudades.service.edificio.IEdificioService;

import java.util.List;

@RestController
@RequestMapping("/api/edificios")
public class EdificioController {
    @Autowired
    private IEdificioService edificioService;

    @GetMapping
    public List<EdificioDto> obtenerTodosLosEdificios() {
        return edificioService.obtenerTodosLosEdificios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EdificioDto> obtenerEdificioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(edificioService.obtenerEdificioPorId(id));
    }

    @PostMapping
    public ResponseEntity<EdificioDto> crearEdificio(@RequestBody EdificioDto edificioDto) {
        return new ResponseEntity<>(edificioService.crearEdificio(edificioDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EdificioDto> actualizarEdificio(@PathVariable Long id, @RequestBody EdificioDto edificioDto) {
        return ResponseEntity.ok(edificioService.actualizarEdificio(id, edificioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEdificio(@PathVariable Long id) {
        edificioService.eliminarEdificio(id);
        return ResponseEntity.noContent().build();
    }
}
