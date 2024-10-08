package com.build.api.steps;

import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.edificio.Edificio;
import com.build.api.model.edificio.Tipo_edificio;
import com.build.api.model.recurso.Recurso;
import com.build.api.repository.CiudadRepository;
import com.build.api.repository.EdificioRepository;
import com.build.api.repository.RecursoRepository;
import com.build.api.service.ciudadservice.CiudadService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;


@SpringBootTest
public class RecompensaSteps {
  @Autowired
  private CiudadService ciudadService;

  @Autowired
  private CiudadRepository ciudadRepository;

  @Autowired
  private EdificioRepository edificioRepository;

  @Autowired
  private RecursoRepository recursoRepository;

  private Ciudad ciudad;
  private boolean objetivoAlcanzado;

  @Given("el jugador tiene una ciudad con un nivel actual de {int}")
  public void el_jugador_tiene_una_ciudad_con_un_nivel_actual_de(int nivelInicial) {
    ciudad = new Ciudad();
    ciudad.setNombre("Ciudad de Prueba");
    ciudad.setNivel(nivelInicial);
    ciudadRepository.save(ciudad);
  }

  @Given("el jugador ha construido {int} edificios")
  public void el_jugador_ha_construido_edificios(int numeroEdificios) {
    for (int i = 0; i < numeroEdificios; i++) {
      Edificio edificio = new Edificio();
      edificio.setNombre("Edificio " + i);
      edificio.setCiudad(ciudad);
      edificio.setTipoEdificio(Tipo_edificio.CLASE_BAJA);
      edificioRepository.save(edificio);
    }
    ciudad.setEdificios(edificioRepository.findByCiudad(ciudad));
  }

  @When("el sistema detecta que el jugador ha alcanzado el objetivo de {int} edificios")
  public void el_sistema_detecta_que_el_jugador_ha_alcanzado_el_objetivo_de_edificios(int objetivoEdificios) {
    objetivoAlcanzado = ciudadService.verificarYSubirNivelCiudad(ciudad.getId(), objetivoEdificios);
  }

  @Then("la ciudad sube al nivel {int}")
  public void la_ciudad_sube_al_nivel(int nivelEsperado) {
    Ciudad ciudadActualizada = ciudadRepository.findById(ciudad.getId()).orElseThrow();
    assertTrue(objetivoAlcanzado);
    assertEquals(nivelEsperado, ciudadActualizada.getNivel());
  }

  @Then("el jugador recibe una recompensa de {int} unidades adicionales de cada recurso")
  public void el_jugador_recibe_una_recompensa_de_unidades_adicionales_de_cada_recurso(int unidadesRecompensa) {
    List<Recurso> recursos = recursoRepository.findByCiudad(ciudad);

    for (Recurso recurso : recursos) {
      int cantidadEsperada = recurso.getCantidad() + unidadesRecompensa;
      assertEquals(cantidadEsperada, recurso.getCantidad());
    }
  }
}
