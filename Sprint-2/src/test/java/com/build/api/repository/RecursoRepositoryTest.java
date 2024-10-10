package com.build.api.repository;

import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.recurso.Recurso;
import com.build.api.model.recurso.Tipo_recurso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class RecursoRepositoryTest {

  @Autowired
  private RecursoRepository recursoRepository;

  @Autowired
  private CiudadRepository ciudadRepository;

  private Ciudad ciudad;
  private Recurso recurso1, recurso2;

  @BeforeEach
  public void setUp() {
    ciudad = new Ciudad();
    ciudad.setNombre("CiudadTest");
    ciudadRepository.save(ciudad);

    recurso1 = new Recurso(Tipo_recurso.ORO, 100, ciudad);
    recurso2 = new Recurso(Tipo_recurso.PIEDRA, 200, ciudad);
    recursoRepository.save(recurso1);
    recursoRepository.save(recurso2);
  }

  @Test
  public void testFindByTipoRecursoAndCiudad(){
    List<Recurso> recursos = recursoRepository.findByTipoRecursosAndCiudad(Tipo_recurso.ORO, ciudad);
    assertThat(recursos).isNotEmpty();
    assertThat(recursos.get(0).getTipoRecursos()).isEqualTo(Tipo_recurso.ORO);
    assertThat(recursos.get(0).getCiudad()).isEqualTo(ciudad);
    assertThat(recursos.get(0).getCantidad()).isEqualTo(100);
  }

  @Test
  public void testFindByTipoRecursos(){
    List<Recurso> recursosOro = recursoRepository.findByTipoRecursos(Tipo_recurso.ORO);
    List<Recurso> recursosPiedra = recursoRepository.findByTipoRecursos(Tipo_recurso.PIEDRA);

    assertThat(recursosOro).hasSize(1);
    assertThat(recursosOro.get(0).getTipoRecursos()).isEqualTo(Tipo_recurso.ORO);
    assertThat(recursosOro.get(0).getCantidad()).isEqualTo(100);

    assertThat(recursosPiedra).hasSize(1);
    assertThat(recursosPiedra.get(0).getTipoRecursos()).isEqualTo(Tipo_recurso.PIEDRA);
    assertThat(recursosPiedra.get(0).getCantidad()).isEqualTo(200);
  }
}