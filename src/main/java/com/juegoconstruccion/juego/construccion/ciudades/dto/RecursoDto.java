package com.juegoconstruccion.juego.construccion.ciudades.dto;

import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;

public class RecursoDto {
    private Long id;
    private Tipo_recurso tipoRecursos;
    private int cantidad;
    private Long ciudadId;

    public RecursoDto(){}

    public RecursoDto(Long id, Tipo_recurso tipoRecursos, int cantidad, Long ciudadId) {
        this.id = id;
        this.tipoRecursos = tipoRecursos;
        this.cantidad = cantidad;
        this.ciudadId = ciudadId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo_recurso getTipoRecursos() {
        return tipoRecursos;
    }

    public void setTipoRecursos(Tipo_recurso tipoRecursos) {
        this.tipoRecursos = tipoRecursos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }
}
