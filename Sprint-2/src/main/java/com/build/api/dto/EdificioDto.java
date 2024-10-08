package com.build.api.dto;


import com.build.api.model.edificio.Tipo_edificio;
import com.build.api.model.recurso.Tipo_recurso;
import lombok.Data;

import java.util.Map;

@Data
public class EdificioDto {
    private Long id;
    private String nombre;
    private Tipo_edificio tipoEdificio;
    private Map<Tipo_recurso, Integer> costo;
    private boolean construccionCompleta;
    private Long ciudadId;

    public EdificioDto() {}

    public EdificioDto(Long id, String nombre, Tipo_edificio tipoEdificio, Map<Tipo_recurso, Integer> costo, boolean construccionCompleta, Long ciudadId) {
        this.id = id;
        this.nombre = nombre;
        this.tipoEdificio = tipoEdificio;
        this.costo = costo;
        this.construccionCompleta = construccionCompleta;
        this.ciudadId = ciudadId;
    }

    public EdificioDto(String nombre, Long ciudadId, Tipo_edificio tipoEdificio, Map<Tipo_recurso, Integer> costo){
        this.nombre = nombre;
        this.ciudadId = ciudadId;
        this.tipoEdificio = tipoEdificio;
        this.costo = costo;
    }
}
