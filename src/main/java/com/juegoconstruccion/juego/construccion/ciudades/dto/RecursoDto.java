package com.juegoconstruccion.juego.construccion.ciudades.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;

/**
 * Data Transfer Object (DTO) para representar un recurso en la aplicación.
 * Esta clase encapsula la información relacionada con un recurso específico,
 * incluyendo su tipo, cantidad disponible y la ciudad a la que está asociado.
 */
public class RecursoDto {
    private Long id;

    @JsonProperty("tipoRecurso")
    private Tipo_recurso tipoRecursos;
    private int cantidad;
    private Long ciudadId;

    /**
     * Constructor por defecto.
     */
    public RecursoDto(){}

    /**
     * Constructor que inicializa el DTO con los parámetros especificados.
     *
     * @param id ID del recurso.
     * @param tipoRecursos Tipo de recurso.
     * @param cantidad Cantidad disponible del recurso.
     * @param ciudadId ID de la ciudad a la que está asociado el recurso.
     */
    public RecursoDto(Long id, Tipo_recurso tipoRecursos, int cantidad, Long ciudadId) {
        this.id = id;
        this.tipoRecursos = tipoRecursos;
        this.cantidad = cantidad;
        this.ciudadId = ciudadId;
    }

    /**
     * Obtiene el ID del recurso.
     *
     * @return ID del recurso.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del recurso.
     *
     * @param id ID a establecer para el recurso.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de recurso.
     *
     * @return Tipo_recurso del recurso.
     */
    public Tipo_recurso getTipoRecursos() {
        return tipoRecursos;
    }

    /**
     * Establece el tipo de recurso.
     *
     * @param tipoRecursos Tipo de recurso a establecer.
     */
    public void setTipoRecursos(Tipo_recurso tipoRecursos) {
        this.tipoRecursos = tipoRecursos;
    }

    /**
     * Obtiene la cantidad disponible del recurso.
     *
     * @return Cantidad del recurso.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad disponible del recurso.
     *
     * @param cantidad Cantidad a establecer para el recurso.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el ID de la ciudad a la que está asociado el recurso.
     *
     * @return ID de la ciudad.
     */
    public Long getCiudadId() {
        return ciudadId;
    }

    /**
     * Establece el ID de la ciudad a la que está asociado el recurso.
     *
     * @param ciudadId ID de la ciudad a establecer.
     */
    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }
}
