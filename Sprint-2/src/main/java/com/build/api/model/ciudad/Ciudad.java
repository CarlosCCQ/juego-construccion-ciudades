package com.build.api.model.ciudad;

import com.build.api.model.edificio.Edificio;
import com.build.api.model.generador.Genera_recuso;
import com.build.api.model.recurso.Recurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    public Ciudad() {
    }

    public Ciudad(String nombre, List<Recurso> recursos, List<Genera_recuso> generaRecusos, List<Edificio> edificios) {
        this.nombre = nombre;
        this.recursos = recursos;
        this.generaRecusos = generaRecusos;
        this.edificios = edificios;
    }
}
