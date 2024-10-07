package com.construccion.ciudades.construccion.dto;

import com.construccion.ciudades.construccion.model.generador.Tipo_generador_recurso;
import com.construccion.ciudades.construccion.model.recurso.Tipo_recurso;

public class Genera_recursoDto {
    private Tipo_generador_recurso tipoGeneradorRecurso;
    private Long ciudadId;
    private Tipo_recurso tipoRecursoGenerado;
    private int capacidadGeneracion;

    public Genera_recursoDto() {}

    public Genera_recursoDto(Tipo_generador_recurso tipoGeneradorRecurso, Long ciudadId, Tipo_recurso tipoRecursoGenerado){
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudadId = ciudadId;
        this.tipoRecursoGenerado = tipoRecursoGenerado;
    }

    public Tipo_generador_recurso getTipoGeneradorRecurso() {
        return tipoGeneradorRecurso;
    }

    public void setTipoGeneradorRecurso(Tipo_generador_recurso tipoGeneradorRecurso) {
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    public Tipo_recurso getTipoRecursoGenerado() {
        return tipoRecursoGenerado;
    }

    public void setTipoRecursoGenerado(Tipo_recurso tipoRecursoGenerado) {
        this.tipoRecursoGenerado = tipoRecursoGenerado;
    }

    public int getCapacidadGeneracion() {
        return capacidadGeneracion;
    }

    public void setCapacidadGeneracion(int capacidadGeneracion) {
        this.capacidadGeneracion = capacidadGeneracion;
    }
}
