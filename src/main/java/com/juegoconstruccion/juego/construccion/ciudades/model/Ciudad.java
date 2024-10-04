package com.juegoconstruccion.juego.construccion.ciudades.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.juegoconstruccion.juego.construccion.ciudades.model.edificio.Edificio;

/**
 * La clase Ciudad representa una entidad en el contexto de un juego de construcción de ciudades.
 * Contiene información sobre los recursos, edificios y generadores de recursos asociados con una ciudad.
 */
@Entity(name = "ciudad")
@Table(name = "ciudades")
public class Ciudad {

    // Atributos de la clase Ciudad

    /**
     * El identificador único de la ciudad. Es generado automáticamente usando una estrategia de auto-incrementación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El nombre de la ciudad. Este campo no puede ser nulo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Lista de recursos asociados con la ciudad.
     * Relación Uno a Muchos, donde una ciudad puede tener múltiples recursos.
     * Se utiliza la carga ansiosa (EAGER) para obtener los recursos cuando se consulta la ciudad.
     */
    @OneToMany(mappedBy = "ciudad", fetch = FetchType.EAGER)
    private List<Recurso> recursos = new ArrayList<>();

    /**
     * Lista de objetos que representan la generación de recursos en la ciudad.
     * Relación Uno a Muchos, con carga ansiosa (EAGER).
     */
    @OneToMany(mappedBy = "ciudad", fetch = FetchType.EAGER)
    private List<Genera_recuso> generaRecusos = new ArrayList<>();

    /**
     * Lista de edificios en la ciudad. Una ciudad puede tener múltiples edificios.
     * Relación Uno a Muchos, con carga ansiosa (EAGER).
     */
    @OneToMany(mappedBy = "ciudad", fetch = FetchType.EAGER)
    private List<Edificio> edificios = new ArrayList<>();

    // Constructores

    /**
     * Constructor por defecto, necesario para JPA.
     * Permite crear instancias vacías de Ciudad.
     */
    public Ciudad() {}

    /**
     * Constructor que inicializa una ciudad con un nombre, lista de recursos, generadores de recursos y edificios.
     *
     * @param nombre El nombre de la ciudad.
     * @param recursos La lista de recursos de la ciudad.
     * @param generaRecusos La lista de generadores de recursos.
     * @param edificios La lista de edificios asociados con la ciudad.
     */
    public Ciudad(String nombre, List<Recurso> recursos, List<Genera_recuso> generaRecusos, List<Edificio> edificios) {
        this.nombre = nombre;
        this.recursos = recursos;
        this.generaRecusos = generaRecusos;
        this.edificios = edificios;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador único de la ciudad.
     *
     * @return El ID de la ciudad.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la ciudad.
     *
     * @param id El nuevo ID de la ciudad.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la ciudad.
     *
     * @return El nombre de la ciudad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la ciudad.
     *
     * @param nombre El nuevo nombre de la ciudad.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de recursos de la ciudad.
     *
     * @return La lista de recursos de la ciudad.
     */
    public List<Recurso> getRecursos() {
        return recursos;
    }

    /**
     * Establece la lista de recursos de la ciudad.
     *
     * @param recursos La nueva lista de recursos.
     */
    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    /**
     * Obtiene la lista de generadores de recursos de la ciudad.
     *
     * @return La lista de generadores de recursos.
     */
    public List<Genera_recuso> getGeneraRecusos() {
        return generaRecusos;
    }

    /**
     * Establece la lista de generadores de recursos de la ciudad.
     *
     * @param generaRecusos La nueva lista de generadores de recursos.
     */
    public void setGenerarecusos(List<Genera_recuso> generaRecusos) {
        this.generaRecusos = generaRecusos;
    }

    /**
     * Obtiene la lista de edificios de la ciudad.
     *
     * @return La lista de edificios.
     */
    public List<Edificio> getEdificios() {
        return edificios;
    }

    /**
     * Establece la lista de edificios de la ciudad.
     *
     * @param edificios La nueva lista de edificios.
     */
    public void setEdificios(List<Edificio> edificios) {
        this.edificios = edificios;
    }
}
