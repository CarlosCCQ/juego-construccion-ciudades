package com.construccion.ciudades.construccion.model.ciudad;

import com.construccion.ciudades.construccion.model.edificio.Edificio;
import com.construccion.ciudades.construccion.model.generador.Genera_recuso;
import com.construccion.ciudades.construccion.model.recurso.Recurso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Clase de prueba unitaria para la clase {@link Ciudad}.
 * Esta clase utiliza JUnit para verificar el comportamiento de la clase Ciudad, asegurando que
 * se manejen correctamente los recursos, generadores y edificios de una ciudad.
 */
public class CiudadTest {

    // Atributos para manejar una ciudad de prueba y sus componentes
    private Ciudad ciudad;
    private List<Recurso> recursos;
    private List<Genera_recuso> generadores;
    private List<Edificio> edificios;

    /**
     * Inicializa los objetos y listas necesarias antes de cada prueba.
     * Se crea una ciudad vacía con su nombre y listas de recursos, generadores y edificios vacías.
     */
    @BeforeEach
    public void setup() {
        // Inicializar listas vacías de recursos, generadores y edificios
        recursos = new ArrayList<>();
        generadores = new ArrayList<>();
        edificios = new ArrayList<>();

        // Crear una ciudad con nombre
        ciudad = new Ciudad("Ciudad Lima", recursos, generadores, edificios);
    }

    /**
     * Verifica que al crear una ciudad, el nombre sea correcto y las listas de recursos,
     * generadores y edificios estén vacías inicialmente.
     */
    @Test
    public void testCrearCiudad() {
        // Verificar que el nombre de la ciudad sea correcto
        assertEquals("Ciudad Lima", ciudad.getNombre());

        // Verificar que las listas de recursos, generadores y edificios estén vacías al inicio
        assertTrue(ciudad.getRecursos().isEmpty());
        assertTrue(ciudad.getGeneraRecusos().isEmpty());
        assertTrue(ciudad.getEdificios().isEmpty());
    }

    /**
     * Verifica que se puede añadir correctamente un recurso a la ciudad y que la lista
     * de recursos se actualiza correctamente.
     */
    @Test
    public void testAnadirRecurso() {
        // Añadir un recurso a la ciudad
        Recurso recurso = new Recurso();
        recursos.add(recurso);

        ciudad.setRecursos(recursos);

        // Verificar que el recurso se ha añadido correctamente
        assertEquals(1, ciudad.getRecursos().size());
        assertTrue(ciudad.getRecursos().contains(recurso));
    }

    /**
     * Verifica que se puede añadir correctamente un generador de recursos a la ciudad y que la lista
     * de generadores se actualiza correctamente.
     */
    @Test
    public void testAnadirGenerador() {
        // Añadir un generador de recursos a la ciudad
        Genera_recuso generador = new Genera_recuso();
        generadores.add(generador);

        ciudad.setGenerarecusos(generadores);

        // Verificar que el generador se ha añadido correctamente
        assertEquals(1, ciudad.getGeneraRecusos().size());
        assertTrue(ciudad.getGeneraRecusos().contains(generador));
    }

    /**
     * Verifica que se puede añadir correctamente un edificio a la ciudad y que la lista
     * de edificios se actualiza correctamente.
     */
    @Test
    public void testAnadirEdificio() {
        // Añadir un edificio a la ciudad
        Edificio edificio = new Edificio();
        edificios.add(edificio);

        ciudad.setEdificios(edificios);

        // Verificar que el edificio se ha añadido correctamente
        assertEquals(1, ciudad.getEdificios().size());
        assertTrue(ciudad.getEdificios().contains(edificio));
    }
}
