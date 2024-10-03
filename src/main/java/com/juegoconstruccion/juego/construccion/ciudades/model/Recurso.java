package com.juegoconstruccion.juego.construccion.ciudades.model;

import jakarta.persistence.*;

@Entity(name = "recurso")
@Table(name = "recursos")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo_recurso tipoRecurso;

    @Column(nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    public Recurso(){}

    public Recurso(Tipo_recurso tipoRecurso, int cantidad, Ciudad ciudad){
        this.tipoRecurso = tipoRecurso;
        this.cantidad = cantidad;
        this.ciudad = ciudad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo_recurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(Tipo_recurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
