package com.juegoconstruccion.juego.construccion.ciudades.model.edificio;

import jakarta.persistence.*;
import java.util.List;
import java.util.Map;
import com.juegoconstruccion.juego.construccion.ciudades.model.Ciudad;
import com.juegoconstruccion.juego.construccion.ciudades.model.Recurso;
import com.juegoconstruccion.juego.construccion.ciudades.model.Tipo_recurso;

/**
 * Representa la entidad `Edificio` en el juego de construcción de ciudades.
 * Cada edificio pertenece a una ciudad y tiene un tipo, un costo de construcción 
 * en términos de recursos, y un estado que indica si su construcción está completa.
 */
@Entity(name = "edificio")
@Table(name = "edificios")
public class Edificio {

    /**
     * Identificador único del edificio.
     * Se genera automáticamente utilizando la estrategia de generación de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipo de edificio (por ejemplo, fábrica, granja, etc.).
     * Se almacena como un valor de cadena (string) en la base de datos.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo_edificio tipoEdificio;

    /**
     * Nombre del edificio.
     * Campo obligatorio.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Mapa que representa el costo de construcción del edificio en términos de recursos.
     * La clave del mapa es el tipo de recurso (por ejemplo, madera, piedra), y el valor es la cantidad de dicho recurso necesario.
     * Se almacena en la tabla `edificio_costo`, con columnas para el recurso y la cantidad.
     */
    @ElementCollection
    @CollectionTable(name = "edificio_costo", joinColumns = @JoinColumn(name = "edificio_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "recurso")
    @Column(name = "cantidad")
    private Map<Tipo_recurso, Integer> costo;

    /**
     * Ciudad a la que pertenece el edificio.
     * Relación muchos a uno (many-to-one), ya que una ciudad puede tener varios edificios, 
     * pero cada edificio pertenece a una sola ciudad.
     */
    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    /**
     * Indica si la construcción del edificio está completa o no.
     * Campo obligatorio.
     */
    @Column(nullable = false)
    private boolean construccionCompleta;

    /**
     * Constructor sin parámetros necesario para JPA.
     */
    public Edificio(){}

    /**
     * Constructor con todos los parámetros necesarios para inicializar un edificio.
     * 
     * @param nombre Nombre del edificio.
     * @param tipoEdificio Tipo de edificio.
     * @param costo Mapa que representa el costo de construcción en términos de recursos.
     * @param construccionCompleta Indica si la construcción está completa.
     * @param recursos Lista de recursos asociados.
     * @param ciudad Ciudad a la que pertenece el edificio.
     */
    public Edificio(String nombre, Tipo_edificio tipoEdificio, Map<Tipo_recurso, Integer> costo, boolean construccionCompleta, List<Recurso> recursos, Ciudad ciudad) {
        this.nombre = nombre;
        this.tipoEdificio = tipoEdificio;
        this.costo = costo;
        this.construccionCompleta = construccionCompleta;
        this.ciudad = ciudad;
    }

    // Métodos getter y setter para acceder y modificar los atributos del edificio.

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
