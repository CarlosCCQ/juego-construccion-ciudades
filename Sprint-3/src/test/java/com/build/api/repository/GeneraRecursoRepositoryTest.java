package com.build.api.repository;

import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.generador.Genera_recuso;
import com.build.api.model.generador.Tipo_generador_recurso;
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
class GeneraRecursoRepositoryTest {

  @Autowired
  private GeneraRecursoRepository generaRecursoRepository;

  @Autowired
  private CiudadRepository ciudadRepository;

  @Autowired
  private RecursoRepository recursoRepository;

  private Ciudad ciudad;
  private Recurso recursoOro, recursoPiedra;
  private Genera_recuso generaRecuso1, generaRecuso2;

  @BeforeEach
  public void setUp() {
    ciudad = new Ciudad();
    ciudad.setNombre("CiudadTest");
    ciudad = ciudadRepository.save(ciudad);

    recursoOro = new Recurso(Tipo_recurso.ORO, 100, ciudad);
    recursoPiedra = new Recurso(Tipo_recurso.PIEDRA, 200, ciudad);
    recursoRepository.save(recursoOro);
    recursoRepository.save(recursoPiedra);

    generaRecuso1 = new Genera_recuso();
    generaRecuso1.setCiudad(ciudad);
    generaRecuso1.setTipoGeneradorRecurso(Tipo_generador_recurso.CANTERAS);
    generaRecuso1.setRecursoGenerado(recursoPiedra);

    generaRecuso2 = new Genera_recuso();
    generaRecuso2.setCiudad(ciudad);
    generaRecuso2.setTipoGeneradorRecurso(Tipo_generador_recurso.MINAS);
    generaRecuso2.setRecursoGenerado(recursoOro);

    generaRecursoRepository.save(generaRecuso1);
    generaRecursoRepository.save(generaRecuso2);
  }

  @Test
  public void testFindByCiudad(){
    List<Genera_recuso> recursos = generaRecursoRepository.findByCiudad(ciudad);

    assertThat(recursos).isNotEmpty();
    assertThat(recursos).hasSize(2);
    assertThat(recursos.get(0).getCiudad()).isEqualTo(ciudad);
    assertThat(recursos.get(0).getTipoGeneradorRecurso()).isEqualTo(Tipo_generador_recurso.CANTERAS);
    assertThat(recursos.get(1).getCiudad()).isEqualTo(ciudad);
    assertThat(recursos.get(1).getTipoGeneradorRecurso()).isEqualTo(Tipo_generador_recurso.MINAS);
  }
}