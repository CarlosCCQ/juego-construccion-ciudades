package com.construccion.ciudades.construccion.model.generador;

import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import com.construccion.ciudades.construccion.model.recurso.Recurso;
import jakarta.persistence.*;

@Entity(name="generado")
@Table(name = "generador")
public class Genera_recuso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Genera_recuso(){}

    public Genera_recuso(Tipo_generador_recurso tipoGeneradorRecurso, Ciudad ciudad, Recurso recursoGenerado, int capacidadGeneracion){
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudad = ciudad;
        this.recursoGenerado = recursoGenerado;
        this.capacidadGeneracion =capacidadGeneracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo_generador_recurso getTipoGeneradorRecurso() {
        return tipoGeneradorRecurso;
    }

    public void setTipoGeneradorRecurso(Tipo_generador_recurso tipoGeneradorRecurso) {
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Recurso getRecursoGenerado() {
        return recursoGenerado;
    }

    public void setRecursoGenerado(Recurso recursoGenerado) {
        this.recursoGenerado = recursoGenerado;
    }

    public int getCapacidadGeneracion() {
        return capacidadGeneracion;
    }

    public void setCapacidadGeneracion(int capacidadGeneracion) {
        this.capacidadGeneracion = capacidadGeneracion;
    }
}
