package com.build.api.controller;

import com.build.api.dto.CiudadDto;
import com.build.api.service.ciudadservice.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {
    @Autowired
    private CiudadService ciudadService;

    @GetMapping
    public List<CiudadDto> obtenerTodasLasCiudades() {
        return ciudadService.obtenerTodasLasCiudades();
    }

    @GetMapping("/{id}")
    public CiudadDto obtenerCiudadPorId(@PathVariable Long id) {
        return ciudadService.obtenerCiudadPorId(id);
    }

    @PostMapping
    public CiudadDto crearCiudad(@RequestBody CiudadDto ciudadDto) {
        return ciudadService.crearCiudad(ciudadDto);
    }

    @PutMapping("/{id}")
    public CiudadDto actualizarCiudad(@PathVariable Long id, @RequestBody CiudadDto ciudadDto) {
        return ciudadService.actualizarCiudad(id, ciudadDto);
    }

    @DeleteMapping("/{id}")
    public void eliminarCiudad(@PathVariable Long id) {
        ciudadService.eliminarCiudad(id);
    }

    @PostMapping("/{ciudadId}/recursos/{recursoId}")
    public void agregarRecursoAlaCiudad(@PathVariable Long ciudadId, @PathVariable Long recursoId) {
        ciudadService.agregarRecursoAlaCiudad(ciudadId, recursoId);
    }

    @PostMapping("/{ciudadId}/generadores/{generaRecursoId}")
    public void agregarGeneradorAlaCiudad(@PathVariable Long ciudadId, @PathVariable Long generaRecursoId) {
        ciudadService.agregarGeneradorAlaCiudad(ciudadId, generaRecursoId);
    }

    @PostMapping("/{ciudadId}/edificios/{edificioId}")
    public void agregarEdificioAlaCiudad(@PathVariable Long ciudadId, @PathVariable Long edificioId) {
        ciudadService.agregarEdificioAlaCiudad(ciudadId, edificioId);
    }
}
