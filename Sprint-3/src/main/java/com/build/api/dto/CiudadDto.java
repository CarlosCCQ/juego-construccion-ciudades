package com.build.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class CiudadDto {
    private Long id;
    private String nombre;
    private List<Long> recursoIds;
    private List<Long> generaRecursoIds;
    private List<Long> edificioIds;

    public CiudadDto(){}

    public CiudadDto(Long id, String nombre, List<Long> recursoIds, List<Long> generaRecursoIds, List<Long> edificioIds) {
        this.id = id;
        this.nombre = nombre;
        this.recursoIds = recursoIds;
        this.generaRecursoIds = generaRecursoIds;
        this.edificioIds = edificioIds;
    }
}
