package com.juegoconstruccion.juego.construccion.ciudades.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un generador de recursos en una ciudad.
 * Esta clase define la relación entre un generador de recursos,
 * el tipo de recurso que genera, la ciudad a la que está asociado
 * y su capacidad de generación.
 */
@Entity(name="generado")
@Table(name = "generador")
public class Genera_recuso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo_generador_recurso tipoGeneradorRecurso;

    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recursoGenerado;

    @Column(nullable = false)
    private int capacidadGeneracion;

    /**
     * Constructor por defecto.
     */
    public Genera_recuso(){}

    /**
     * Constructor que inicializa la entidad con los parámetros especificados.
     *
     * @param tipoGeneradorRecurso Tipo de generador de recurso.
     * @param ciudad Ciudad a la que está asociado este generador.
     * @param recursoGenerado Recurso que genera este generador.
     * @param capacidadGeneracion Capacidad de generación del generador.
     */
    public Genera_recuso(Tipo_generador_recurso tipoGeneradorRecurso, Ciudad ciudad, Recurso recursoGenerado, int capacidadGeneracion){
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudad = ciudad;
        this.recursoGenerado = recursoGenerado;
        this.capacidadGeneracion =capacidadGeneracion;
    }

    /**
     * Obtiene el ID del generador de recursos.
     *
     * @return ID del generador.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del generador de recursos.
     *
     * @param id ID a establecer para el generador.
     */
    public void setId(Long id) {
        this.id = id;
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
     * Obtiene la ciudad asociada a este generador.
     *
     * @return Ciudad a la que está asociado el generador.
     */
    public Ciudad getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad asociada a este generador.
     *
     * @param ciudad Ciudad a establecer para el generador.
     */
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el recurso que genera este generador.
     *
     * @return Recurso generado por el generador.
     */
    public Recurso getRecursoGenerado() {
        return recursoGenerado;
    }

    /**
     * Establece el recurso que genera este generador.
     *
     * @param recursoGenerado Recurso a establecer para el generador.
     */
    public void setRecursoGenerado(Recurso recursoGenerado) {
        this.recursoGenerado = recursoGenerado;
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
