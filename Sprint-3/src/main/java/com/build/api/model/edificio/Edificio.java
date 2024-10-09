package com.build.api.model.edificio;

import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.recurso.Tipo_recurso;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;


@Entity(name = "edificio")
@Table(name = "edificios")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Edificio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo_edificio tipoEdificio;

    @Column(nullable = false)
    private String nombre;

    @ElementCollection
    @CollectionTable(name = "edificio_costo", joinColumns = @JoinColumn(name = "edificio_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "recurso")
    @Column(name = "cantidad")
    private Map<Tipo_recurso, Integer> costo;

    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    @Column(nullable = false)
    private boolean construccionCompleta;

}