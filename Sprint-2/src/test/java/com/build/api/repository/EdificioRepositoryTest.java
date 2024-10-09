package com.build.api.repository;

import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.edificio.Edificio;
import com.build.api.model.edificio.Tipo_edificio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = CiudadRepository.class)
class EdificioRepositoryTest {

  @Autowired
  private EdificioRepository edificioRepository;

  @Autowired
  private CiudadRepository ciudadRepository;

  private Ciudad ciudad;

  @BeforeEach
  void setUp(){
    ciudad = new Ciudad();
    ciudad.setNombre("Ciudad prueba");
    ciudad = ciudadRepository.save(ciudad);
  }


  @Test
  void edificio_repository_guardar_edificio(){
    //Given : Un edificio nuevo
    Edificio edificio = Edificio.builder().nombre("Edificio de prueba").ciudad(ciudad).tipoEdificio(Tipo_edificio.CLASE_BAJA).build();
    edificioRepository.save(edificio);
    //When: Se recupera el edicio de la base de datos
    Edificio edificio_guardado = edificioRepository.findById(edificio.getId()).get();
    //Then: El edifico guardado debe tener el mismo nombre
    Assertions.assertThat(edificio_guardado.getNombre()).isEqualTo(edificio.getNombre());
  }
}