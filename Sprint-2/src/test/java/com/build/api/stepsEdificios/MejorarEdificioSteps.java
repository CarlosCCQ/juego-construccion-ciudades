package com.build.api.stepsEdificios;

import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.edificio.Edificio;
import com.build.api.model.edificio.Tipo_edificio;
import com.build.api.model.recurso.Recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.repository.CiudadRepository;
import com.build.api.repository.EdificioRepository;
import com.build.api.repository.RecursoRepository;
import com.build.api.service.edificioservice.EdificioService;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;


@SpringBootTest
public class MejorarEdificioSteps {

  @Autowired
  private EdificioService edificioService;

  @Autowired
  private CiudadRepository ciudadRepository;

  @Autowired
  private EdificioRepository edificioRepository;

  @Autowired
  private RecursoRepository recursoRepository;

  private Ciudad ciudad;
  private Edificio edificio;
  private boolean mejoraExitosa;

  @Given("el jugador tiene un edificio de tipo {string}")
  public void el_jugador_tiene_un_edificio_de_tipo(String tipo) {
    // Configuración de la ciudad y guardado en el repositorio
    ciudad = new Ciudad();
    ciudad.setNombre("Ciudad");
    ciudadRepository.save(ciudad);

    edificio = new Edificio();
    edificio.setNombre("Casa");
    edificio.setTipoEdificio((Tipo_edificio.fromString(tipo)));
    edificio.setCiudad(ciudad);
    edificioRepository.save(edificio);
  }

  @Given("el jugador tiene suficientes recursos para mejorar")
  public void el_jugador_tiene_suficientes_recursos_para_mejorar() {
    Recurso piedra = new Recurso(Tipo_recurso.PIEDRA, 20, ciudad);
    Recurso oro = new Recurso(Tipo_recurso.ORO, 10, ciudad);
    Recurso agua = new Recurso(Tipo_recurso.AGUA, 5, ciudad);
    recursoRepository.save(piedra);
    recursoRepository.save(oro);
    recursoRepository.save(agua);
  }

  @When("el sistema verifica los recursos disponibles")
  public void el_sistema_verifica_los_recursos_disponibles() {
    mejoraExitosa = edificioService.mejorarEdificio(edificio.getId());
  }

  @Then("el edificio se mejora al siguiente nivel o tipo")
  public void el_edificio_se_mejora_al_siguiente_nivel_o_tipo() {
    Edificio edificioActualizado = edificioRepository.findById(edificio.getId()).orElse(null);

    assertNotNull(edificioActualizado);
    assertTrue(mejoraExitosa);
    assertEquals(Tipo_edificio.CLASE_MEDIA, edificioActualizado.getTipoEdificio());
  }
}
