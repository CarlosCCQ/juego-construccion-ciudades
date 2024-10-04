package com.juegoconstruccion.juego.construccion.ciudades.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un recurso en una ciudad.
 * Esta clase define la relación entre el tipo de recurso,
 * la cantidad disponible y la ciudad a la que pertenece.
 */
@Entity(name = "recurso")
@Table(name = "recursos")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo_recurso tipoRecursos;

    @Column(nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    /**
     * Constructor por defecto.
     */
    public Recurso(){}

    /**
     * Constructor que inicializa la entidad con los parámetros especificados.
     *
     * @param tipoRecursos Tipo de recurso.
     * @param cantidad Cantidad disponible del recurso.
     * @param ciudad Ciudad a la que está asociado este recurso.
     */
    public Recurso(Tipo_recurso tipoRecursos, int cantidad, Ciudad ciudad){
        this.tipoRecursos = tipoRecursos;
        this.cantidad = cantidad;
        this.ciudad = ciudad;
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
     * Obtiene la ciudad asociada a este recurso.
     *
     * @return Ciudad a la que está asociado el recurso.
     */
    public Ciudad getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad asociada a este recurso.
     *
     * @param ciudad Ciudad a establecer para el recurso.
     */
    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
