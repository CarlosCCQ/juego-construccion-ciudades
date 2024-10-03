package com.juegoconstruccion.juego.construccion.ciudades.dto;

import java.util.Map;

import com.juegoconstruccion.juego.construccion.ciudades.model.edificio.Tipo_edificio;

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

    public Tipo_edificio getTipoEdificio() {
        return tipoEdificio;
    }

    public void setTipoEdificio(Tipo_edificio tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    public Map<Tipo_recurso, Integer> getCosto() {
        return costo;
    }

    public void setCosto(Map<Tipo_recurso, Integer> costo) {
        this.costo = costo;
    }

    public boolean isConstruccionCompleta() {
        return construccionCompleta;
    }

    public void setConstruccionCompleta(boolean construccionCompleta) {
        this.construccionCompleta = construccionCompleta;
    }

    public Long getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }
}
