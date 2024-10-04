package com.juegoconstruccion.juego.construccion.ciudades.model;

/**
 * Enumeración que define los tipos de generadores de recursos en el juego.
 * Los generadores de recursos son entidades que producen diferentes tipos de recursos
 * utilizados en la construcción y el desarrollo de ciudades.
 */
public enum Tipo_generador_recurso {
    /**
     * Representa un generador de recursos de tipo cantera,
     * que produce recursos relacionados con la minería de piedra.
     */
    CANTERAS,

    /**
     * Representa un generador de recursos de tipo mina,
     * que produce recursos relacionados con la minería de metales como el oro.
     */
    MINAS,

    /**
     * Representa un generador de recursos de tipo río,
     * que produce recursos relacionados con el agua.
     */
    RIO
}
