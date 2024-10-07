package com.construccion.ciudades.construccion.model.edificio;

import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import com.construccion.ciudades.construccion.model.recurso.Recurso;
import com.construccion.ciudades.construccion.model.recurso.Tipo_recurso;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity(name = "edificio")
@Table(name = "edificios")
public class Edificio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Edificio(){}

    public Edificio(String nombre, Tipo_edificio tipoEdificio, Map<Tipo_recurso, Integer> costo, boolean construccionCompleta, List<Recurso> recursos, Ciudad ciudad){
        this.nombre = nombre;
        this.tipoEdificio = tipoEdificio;
        this.costo = costo;
        this.construccionCompleta = construccionCompleta;
        this.ciudad = ciudad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo_edificio getTipoEdificio() {
        return tipoEdificio;
    }

    public void setTipoEdificio(Tipo_edificio tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<Tipo_recurso, Integer> getCosto() {
        return costo;
    }

    public void setCosto(Map<Tipo_recurso, Integer> costo) {
        this.costo = costo;
    }

    public boolean isConstruccionCompleta() {
        return construccionCompleta;
    }

    public void setConstruccionCompleta(boolean construccionCompleta) {
        this.construccionCompleta = construccionCompleta;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
