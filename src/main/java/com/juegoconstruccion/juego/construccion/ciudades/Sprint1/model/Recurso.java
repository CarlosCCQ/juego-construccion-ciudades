package com.juegoconstruccion.juego.construccion.ciudades.model;

import jakarta.persistence.*;

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

    public Recurso(){}

    public Recurso(Tipo_recurso tipoRecursos, int cantidad, Ciudad ciudad){
        this.tipoRecursos = tipoRecursos;
        this.cantidad = cantidad;
        this.ciudad = ciudad;
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

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
