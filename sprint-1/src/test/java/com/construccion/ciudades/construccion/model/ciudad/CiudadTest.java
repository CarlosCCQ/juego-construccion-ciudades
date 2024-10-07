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

public class CiudadTest {

    private Ciudad ciudad;
    private List<Recurso> recursos;
    private List<Genera_recuso> generadores;
    private List<Edificio> edificios;

    @BeforeEach
    public void setup() {
        // Inicializar listas vacías de recursos, generadores y edificios
        recursos = new ArrayList<>();
        generadores = new ArrayList<>();
        edificios = new ArrayList<>();

        // Crear una ciudad con nombre
        ciudad = new Ciudad("Ciudad Ejemplo", recursos, generadores, edificios);
    }

    @Test
    public void testCrearCiudad() {
        // Verificar que el nombre de la ciudad sea correcto
        assertEquals("Ciudad Ejemplo", ciudad.getNombre());

        // Verificar que las listas de recursos, generadores y edificios estén vacías al inicio
        assertTrue(ciudad.getRecursos().isEmpty());
        assertTrue(ciudad.getGeneraRecusos().isEmpty());
        assertTrue(ciudad.getEdificios().isEmpty());
    }

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
