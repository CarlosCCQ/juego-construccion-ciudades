package com.juegoconstruccion.juego.construccion.ciudades.dto;

import java.util.List;

/**
 * La clase CiudadDto es un objeto de transferencia de datos (DTO) que se utiliza
 * para transferir información sobre una ciudad entre distintas capas de la aplicación.
 *
 * Un DTO suele contener solo datos y no lógica de negocio. Este DTO contiene información
 * básica sobre una ciudad, incluyendo su ID, nombre, y las listas de IDs que representan
 * recursos, generadores de recursos, y edificios asociados.
 */
public class CiudadDto {

    // Atributos del DTO

    /**
     * El identificador único de la ciudad.
     */
    private Long id;

    /**
     * El nombre de la ciudad.
     */
    private String nombre;

    /**
     * Lista de identificadores de los recursos asociados con la ciudad.
     */
    private List<Long> recursoIds;

    /**
     * Lista de identificadores de los generadores de recursos asociados con la ciudad.
     */
    private List<Long> generaRecursoIds;

    /**
     * Lista de identificadores de los edificios asociados con la ciudad.
     */
    private List<Long> edificioIds;

    // Constructores

    /**
     * Constructor por defecto. Se necesita para crear instancias vacías del DTO.
     */
    public CiudadDto() {}

    /**
     * Constructor con parámetros que permite inicializar el DTO con todos sus atributos.
     *
     * @param id El ID de la ciudad.
     * @param nombre El nombre de la ciudad.
     * @param recursoIds Lista de identificadores de recursos.
     * @param generaRecursoIds Lista de identificadores de generadores de recursos.
     * @param edificioIds Lista de identificadores de edificios.
     */
    public CiudadDto(Long id, String nombre, List<Long> recursoIds, List<Long> generaRecursoIds, List<Long> edificioIds) {
        this.id = id;
        this.nombre = nombre;
        this.recursoIds = recursoIds;
        this.generaRecursoIds = generaRecursoIds;
        this.edificioIds = edificioIds;
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
     * Obtiene la lista de identificadores de recursos asociados con la ciudad.
     *
     * @return La lista de IDs de recursos.
     */
    public List<Long> getRecursoIds() {
        return recursoIds;
    }

    /**
     * Establece la lista de identificadores de recursos asociados con la ciudad.
     *
     * @param recursoIds La nueva lista de IDs de recursos.
     */
    public void setRecursoIds(List<Long> recursoIds) {
        this.recursoIds = recursoIds;
    }

    /**
     * Obtiene la lista de identificadores de generadores de recursos asociados con la ciudad.
     *
     * @return La lista de IDs de generadores de recursos.
     */
    public List<Long> getGeneraRecursoIds() {
        return generaRecursoIds;
    }

    /**
     * Establece la lista de identificadores de generadores de recursos asociados con la ciudad.
     *
     * @param generaRecursoIds La nueva lista de IDs de generadores de recursos.
     */
    public void setGeneraRecursoIds(List<Long> generaRecursoIds) {
        this.generaRecursoIds = generaRecursoIds;
    }

    /**
     * Obtiene la lista de identificadores de edificios asociados con la ciudad.
     *
     * @return La lista de IDs de edificios.
     */
    public List<Long> getEdificioIds() {
        return edificioIds;
    }

    /**
     * Establece la lista de identificadores de edificios asociados con la ciudad.
     *
     * @param edificioIds La nueva lista de IDs de edificios.
     */
    public void setEdificioIds(List<Long> edificioIds) {
        this.edificioIds = edificioIds;
    }
}
