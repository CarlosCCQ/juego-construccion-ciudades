package com.build.api.stepsEdificio;

import com.build.api.dto.EdificioDto;
import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.edificio.Edificio;
import com.build.api.model.edificio.Tipo_edificio;
import com.build.api.model.recurso.Recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.repository.CiudadRepository;
import com.build.api.repository.RecursoRepository;
import com.build.api.service.edificioservice.EdificioService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
public class CrearEdificioSteps {

  @Autowired
  private EdificioService edificioService;

  @Autowired
  private CiudadRepository ciudadRepository;


  @Autowired
  private RecursoRepository recursoRepository;

  private Ciudad ciudad;

  private Edificio edificio;

  private Map<Tipo_recurso, Integer> recursosDisponibles;
  private boolean construccionExitosa;
  private List<Edificio> edificios = new ArrayList<>();
  @Given("el jugador tiene suficientes recursos")
  public void el_jugador_tiene_suficientes_recursos() {
    ciudad = new Ciudad();
    ciudad.setNombre("Ciudad Test");

    recursosDisponibles = new HashMap<>();
    recursosDisponibles.put(Tipo_recurso.PIEDRA, 100);
    recursosDisponibles.put(Tipo_recurso.ORO, 100);
    recursosDisponibles.put(Tipo_recurso.AGUA, 100);

    List<Recurso> listaRecursos = new ArrayList<>();
    for (Map.Entry<Tipo_recurso, Integer> entry : recursosDisponibles.entrySet()) {
      listaRecursos.add(new Recurso(entry.getKey(), entry.getValue(), ciudad));
    }
    ciudad.setRecursos(listaRecursos);


    ciudadRepository.save(ciudad);
    listaRecursos.forEach(recurso -> recursoRepository.save(recurso));

  }
  @When("el jugador selecciona un tipo de edificio para construir")
  public void el_jugador_selecciona_un_tipo_de_edificio_para_construir() {
    edificio = new Edificio();
    edificio.setNombre("Casa");
    edificio.setTipoEdificio(Tipo_edificio.CLASE_BAJA);
    edificio.setCiudad(ciudad);

    Map<Tipo_recurso, Integer> costoEdificio = new HashMap<>();
    costoEdificio.put(Tipo_recurso.PIEDRA, 5);
    costoEdificio.put(Tipo_recurso.ORO, 2);
    costoEdificio.put(Tipo_recurso.AGUA, 1);
    edificio.setCosto(costoEdificio);
    edificios.add(edificio);
    ciudad.setEdificios(edificios);

    try {
      edificioService.crearEdificio(new EdificioDto(edificio.getNombre(), ciudad.getId(), Tipo_edificio.CLASE_BAJA, costoEdificio));
      construccionExitosa = true;
    } catch (Exception e) {
      construccionExitosa = false;
      throw new AssertionError("No se puede crear un edificio: " + e.getMessage());
    }
  }
  @Then("el edificio seleccionado se añade a la ciudad del jugador")
  public void el_edificio_seleccionado_se_añade_a_la_ciudad_del_jugador() {
    assertTrue(construccionExitosa);
    assertNotNull(ciudad.getEdificios());
    assertEquals("Casa", ciudad.getEdificios().get(0).getNombre());
  }
  @Then("los recursos necesarios para construir el edificio se descuentan del inventario del jugador")
  public void los_recursos_necesarios_para_construir_el_edificio_se_descuentan_del_inventario_del_jugador() {

    ciudad = ciudadRepository.findById(ciudad.getId()).orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

    for (Map.Entry<Tipo_recurso, Integer> entry : edificio.getCosto().entrySet()) {
      Tipo_recurso tipoRecurso = entry.getKey();
      int cantidadRequerida = entry.getValue();

      Recurso recurso = ciudad.getRecursos().stream()
              .filter(r -> r.getTipoRecursos() == tipoRecurso)
              .findFirst()
              .orElseThrow(() -> new RuntimeException("Recurso no encontrado en la ciudad"));

      int cantidadEsperada = recursosDisponibles.get(tipoRecurso) - cantidadRequerida;
      assertEquals("La cantidad del recurso " + tipoRecurso + " no se descontó correctamente",
              cantidadEsperada, recurso.getCantidad());
    }
  }

}
