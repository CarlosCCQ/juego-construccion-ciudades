package com.construccion.ciudades.construccion.repository;

import com.construccion.ciudades.construccion.model.ciudad.Ciudad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CiudadRepositoryTest {

    @Autowired
    private CiudadRepository ciudadRepository;

    @BeforeEach
    public void setup() {
        // Configurar datos iniciales en la base de datos en memoria
        Ciudad ciudad1 = new Ciudad();
        ciudad1.setNombre("Ciudad Lima");

        Ciudad ciudad2 = new Ciudad();
        ciudad2.setNombre("Ciudad Arequipa");

        ciudadRepository.save(ciudad1);
        ciudadRepository.save(ciudad2);
    }

    @Test
    public void testFindByNombre() {
        // Probar la búsqueda por nombre
        List<Ciudad> ciudades = ciudadRepository.findByNombre("Ciudad Lima");
        assertEquals(1, ciudades.size());
        assertEquals("Ciudad Lima", ciudades.get(0).getNombre());
    }

    @Test
    public void testFindByNombreNotFound() {
        // Probar búsqueda de una ciudad que no existe
        List<Ciudad> ciudades = ciudadRepository.findByNombre("Ciudad Cusco");
        assertTrue(ciudades.isEmpty());
    }
}
