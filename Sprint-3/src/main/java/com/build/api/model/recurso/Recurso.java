package com.build.api.model.recurso;

import com.build.api.model.ciudad.Ciudad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "recurso")
@Table(name = "recursos")
@Setter
@Getter
@AllArgsConstructor
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

}
