package com.juegoconstruccion.juego.construccion.ciudades.Sprint1.dto;

import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Long> getRecursoIds() {
        return recursoIds;
    }

    public void setRecursoIds(List<Long> recursoIds) {
        this.recursoIds = recursoIds;
    }

    public List<Long> getGeneraRecursoIds() {
        return generaRecursoIds;
    }

    public void setGeneraRecursoIds(List<Long> generaRecursoIds) {
        this.generaRecursoIds = generaRecursoIds;
    }

    public List<Long> getEdificioIds() {
        return edificioIds;
    }

    public void setEdificioIds(List<Long> edificioIds) {
        this.edificioIds = edificioIds;
    }
}
