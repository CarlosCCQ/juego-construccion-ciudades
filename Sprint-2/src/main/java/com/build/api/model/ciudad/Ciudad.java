package com.build.api.model.ciudad;

import com.build.api.model.edificio.Edificio;
import com.build.api.model.generador.Genera_recuso;
import com.build.api.model.recurso.Recurso;
import com.build.api.model.recurso.Tipo_recurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity(name = "ciudad")
@Table(name = "ciudades")
@Setter
@Getter
@AllArgsConstructor
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "ciudad", fetch = FetchType.EAGER)
    private List<Recurso> recursos = new ArrayList<>();

    @OneToMany(mappedBy = "ciudad", fetch = FetchType.EAGER)
    private List<Genera_recuso> generaRecusos = new ArrayList<>();

    @OneToMany(mappedBy = "ciudad", fetch = FetchType.EAGER)
    private List<Edificio> edificios = new ArrayList<>();

    private int recursosIniciales;

    @Column(name = "nivel", nullable = false)
    private int nivel = 1;

    public void incrementarNivel() {
        this.nivel += 1;
    }

    public boolean haAlcanzadoObjetivoDeEdificios(int cantidadObjetivo) {
        return this.edificios.size() >= cantidadObjetivo;
    }

    public Ciudad() {
    }

    public Ciudad(String nombre, List<Recurso> recursos, List<Genera_recuso> generaRecusos, List<Edificio> edificios) {
        this.nombre = nombre;
        this.recursos = recursos;
        this.generaRecusos = generaRecusos;
        this.edificios = edificios;
    }

/*    public void addRecurso(Tipo_recurso tipo, int cantidad) {
        Recurso recurso = recursos.stream()
                .filter(r -> r.getTipoRecursos() == tipo)
                .findFirst()
                .orElse(null);

        if (recurso != null) {
            recurso.setCantidad(recurso.getCantidad() + cantidad);
        } else {
            recursos.add(new Recurso(tipo, cantidad, this));
        }
    }

    public boolean tieneRecursosSuficientes(Map<Tipo_recurso, Integer> requerimientos) {
        for (Map.Entry<Tipo_recurso, Integer> entry : requerimientos.entrySet()) {
            Tipo_recurso tipo = entry.getKey();
            int cantidadNecesaria = entry.getValue();

            Recurso recurso = recursos.stream()
                    .filter(r -> r.getTipoRecursos() == tipo)
                    .findFirst()
                    .orElse(null);

            if (recurso == null || recurso.getCantidad() < cantidadNecesaria) {
                return false;
            }
        }
        return true;
    }*/
}
