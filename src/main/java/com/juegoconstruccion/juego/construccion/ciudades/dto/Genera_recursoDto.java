package com.juegoconstruccion.juego.construccion.ciudades.dto;

import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_generador_recurso;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;

/**
 * Data Transfer Object (DTO) para representar un generador de recursos.
 * Esta clase encapsula la información relacionada con un generador específico
 * de recursos en una ciudad, incluyendo su tipo, la ciudad asociada,
 * el tipo de recurso generado y la capacidad de generación.
 */
public class Genera_recursoDto {
    private Tipo_generador_recurso tipoGeneradorRecurso;
    private Long ciudadId;
    private Tipo_recurso tipoRecursoGenerado;
    private int capacidadGeneracion;

    /**
     * Constructor por defecto.
     */
    public Genera_recursoDto() {}

    /*public Genera_recursoDto(Long id, Tipo_generador_recurso tipoGeneradorRecurso, Long ciudadId, Tipo_recurso tipoRecursoGenerado, int capacidadGeneracion) {
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudadId = ciudadId;
        this.tipoRecursoGenerado = tipoRecursoGenerado;
        //this.capacidadGeneracion = capacidadGeneracion;
    }*/

    /**
     * Constructor que inicializa el DTO con los parámetros especificados.
     *
     * @param tipoGeneradorRecurso Tipo de generador de recurso.
     * @param ciudadId ID de la ciudad asociada a este generador.
     * @param tipoRecursoGenerado Tipo de recurso que genera este generador.
     */
    public Genera_recursoDto(Tipo_generador_recurso tipoGeneradorRecurso, Long ciudadId, Tipo_recurso tipoRecursoGenerado){
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudadId = ciudadId;
        this.tipoRecursoGenerado = tipoRecursoGenerado;
    }

    /**
     * Obtiene el tipo de generador de recurso.
     *
     * @return Tipo_generador_recurso del generador.
     */
    public Tipo_generador_recurso getTipoGeneradorRecurso() {
        return tipoGeneradorRecurso;
    }

    /**
     * Establece el tipo de generador de recurso.
     *
     * @param tipoGeneradorRecurso Tipo de generador de recurso a establecer.
     */
    public void setTipoGeneradorRecurso(Tipo_generador_recurso tipoGeneradorRecurso) {
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
    }

    /**
     * Obtiene el ID de la ciudad asociada a este generador.
     *
     * @return ID de la ciudad.
     */
    public Long getCiudadId() {
        return ciudadId;
    }

    /**
     * Establece el ID de la ciudad asociada a este generador.
     *
     * @param ciudadId ID de la ciudad a establecer.
     */
    public void setCiudadId(Long ciudadId) {
        this.ciudadId = ciudadId;
    }

    /**
     * Obtiene el tipo de recurso que genera este generador.
     *
     * @return Tipo_recurso generado por el generador.
     */
    public Tipo_recurso getTipoRecursoGenerado() {
        return tipoRecursoGenerado;
    }

    /**
     * Establece el tipo de recurso que genera este generador.
     *
     * @param tipoRecursoGenerado Tipo de recurso a establecer.
     */
    public void setTipoRecursoGenerado(Tipo_recurso tipoRecursoGenerado) {
        this.tipoRecursoGenerado = tipoRecursoGenerado;
    }

    /**
     * Obtiene la capacidad de generación del generador.
     *
     * @return Capacidad de generación en unidades.
     */
    public int getCapacidadGeneracion() {
        return capacidadGeneracion;
    }

    /**
     * Establece la capacidad de generación del generador.
     *
     * @param capacidadGeneracion Capacidad de generación a establecer en unidades.
     */
    public void setCapacidadGeneracion(int capacidadGeneracion) {
        this.capacidadGeneracion = capacidadGeneracion;
    }
}
