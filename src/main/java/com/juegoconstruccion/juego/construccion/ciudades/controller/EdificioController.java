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
    
    // Inyecta el servicio que gestiona las operaciones relacionadas con los edificios
    @Autowired
    private IEdificioService edificioService;

    /**
     * Método que maneja la solicitud GET para obtener todos los edificios.
     * @return Una lista de DTOs de edificios.
     */
    @GetMapping
    public List<EdificioDto> obtenerTodosLosEdificios() {
        // Llama al servicio para obtener todos los edificios y los devuelve como una lista.
        return edificioService.obtenerTodosLosEdificios();
    }

    /**
     * Método que maneja la solicitud GET para obtener un edificio por su ID.
     * @param id El ID del edificio a obtener.
     * @return Un ResponseEntity con el DTO del edificio encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EdificioDto> obtenerEdificioPorId(@PathVariable Long id) {
        // Llama al servicio para obtener un edificio por su ID y lo devuelve en el cuerpo de la respuesta.
        return ResponseEntity.ok(edificioService.obtenerEdificioPorId(id));
    }

    /**
     * Método que maneja la solicitud POST para crear un nuevo edificio.
     * @param edificioDto El DTO del edificio a crear.
     * @return Un ResponseEntity con el DTO del edificio creado y un estado HTTP 201 (CREATED).
     */
    @PostMapping("/add")
    public ResponseEntity<EdificioDto> crearEdificio(@RequestBody EdificioDto edificioDto) {
        // Llama al servicio para crear un nuevo edificio con los datos proporcionados y devuelve el edificio creado.
        return new ResponseEntity<>(edificioService.crearEdificio(edificioDto), HttpStatus.CREATED);
    }

    /**
     * Método que maneja la solicitud PUT para actualizar un edificio existente.
     * @param id El ID del edificio a actualizar.
     * @param edificioDto El DTO con los nuevos datos del edificio.
     * @return Un ResponseEntity con el DTO del edificio actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EdificioDto> actualizarEdificio(@PathVariable Long id, @RequestBody EdificioDto edificioDto) {
        // Llama al servicio para actualizar el edificio existente con los nuevos datos y devuelve el edificio actualizado.
        return ResponseEntity.ok(edificioService.actualizarEdificio(id, edificioDto));
    }

    /**
     * Método que maneja la solicitud DELETE para eliminar un edificio.
     * @param id El ID del edificio a eliminar.
     * @return Un ResponseEntity con un estado HTTP 204 (NO CONTENT) si la eliminación es exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEdificio(@PathVariable Long id) {
        // Llama al servicio para eliminar el edificio por su ID y devuelve un estado de no contenido (204).
        edificioService.eliminarEdificio(id);
        return ResponseEntity.noContent().build();
    }
}
