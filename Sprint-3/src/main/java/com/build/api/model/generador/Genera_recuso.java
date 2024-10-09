package com.build.api.model.generador;
import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.recurso.Recurso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity(name="generado")
@Table(name = "generador")
@Setter
@Getter
@AllArgsConstructor
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recursoGenerado;

    @Column(nullable = false)
    private int capacidadGeneracion;

    public Genera_recuso() {
    }

    public Genera_recuso(Tipo_generador_recurso tipoGeneradorRecurso, Ciudad ciudad, Recurso recursoGenerado, int capacidadGeneracion) {
        this.tipoGeneradorRecurso = tipoGeneradorRecurso;
        this.ciudad = ciudad;
        this.recursoGenerado = recursoGenerado;
        this.capacidadGeneracion = capacidadGeneracion;
    }
}
