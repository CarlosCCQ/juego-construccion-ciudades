package com.build.api.repository;

import com.build.api.model.ciudad.Ciudad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
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
    Optional<Ciudad> ciudadOpt = ciudadRepository.findByNombre("Ciudad Lima");
    assertTrue(ciudadOpt.isPresent(), "La ciudad no fue encontrada");
    Ciudad ciudad = ciudadOpt.get();
    assertEquals("Ciudad Lima", ciudad.getNombre());
  }

  @Test
  public void testFindByNombreNotFound() {
    // Probar búsqueda de una ciudad que no existe
    Optional<Ciudad> ciudadOptional = ciudadRepository.findByNombre("Ciudad Cusco");
    assertFalse(ciudadOptional.isPresent(), "Se encontró una ciudad cuando no debería");
  }
}