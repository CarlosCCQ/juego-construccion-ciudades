package com.construccion.ciudades.construccion.steps;

import static org.junit.jupiter.api.Assertions.*;

import com.construccion.ciudades.construccion.Application;
import com.construccion.ciudades.construccion.controller.Genera_recursoController;
import com.construccion.ciudades.construccion.dto.Genera_recursoDto;
import com.construccion.ciudades.construccion.dto.RecursoDto;
import com.construccion.ciudades.construccion.model.generador.Tipo_generador_recurso;
import com.construccion.ciudades.construccion.model.recurso.Tipo_recurso;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.construccion.ciudades.construccion.controller.RecursoController;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Application.class)
public class RecursoSteps {

    @Autowired
    private RecursoController recursoController;

    @Autowired
    private Genera_recursoController generaRecursoController;

    private ResponseEntity<List<RecursoDto>> recursosReponse;
    private ResponseEntity<RecursoDto> recursoResponse;
    private RecursoDto recursoCreado;
    private ResponseEntity<Genera_recursoDto> generadorCreado;

    private String ciudadNombre;

    @Given("el jugador tiene generadores de recursos asignados a su ciudad")
    public void elJugadorTieneGeneradoresAsignadosASuCiudad(){
        ciudadNombre = "CiudadPrueba";
        Genera_recursoDto generador1 = new Genera_recursoDto(Tipo_generador_recurso.CANTERAS, 1L , Tipo_recurso.PIEDRA);
        generadorCreado = generaRecursoController.crearGenerador(generador1);
        assertNotNull(generadorCreado);
    }

    @When("el jugador decide generar recursos manualmente")
    public void elJugadorDecideGenerarRecursosManual(){
        recursoResponse = recursoController.obtenerRecursoPorId(1L);
        assertNotNull(recursoResponse);
    }

    @Then("los recursos se añaden a los recursos actuales de la ciudad")
    public void losRecursosSeAñadenALaCiudad(){
        RecursoDto recurso = recursoResponse.getBody();
        assertNotNull(recurso);
        recurso.setCantidad(recurso.getCantidad() + 5);
        recursoController.actualizarRecurso(recurso.getId(), recurso);
        assertEquals(105, recurso.getCantidad());
    }

    @Given("una ciudad tiene generadores de recursos activos")
    public void unaCiudadTieneGeneradoresActivos(){
        recursosReponse = recursoController.obtenerTodosLosRecursos();
        assertFalse(recursosReponse.getBody().isEmpty());
    }

    @When("el tiempo programado de generación de recursos se cumple")
    public void elTiempoProgramadoDeGeneracionDeRecursosSeCumple(){
        recursoResponse = recursoController.obtenerRecursoPorId(1L);
        RecursoDto recurso = recursoResponse.getBody();
        assertNotNull(recurso);
        recurso.setCantidad(recurso.getCantidad() + 10);
        recursoController.actualizarRecurso(recurso.getId(), recurso);
    }

    @Then("los generadores de recursos añaden más recursos a la ciudad")
    public void losGeneradoresAñadenMasRecursos() {
        RecursoDto recurso = recursoResponse.getBody();
        assertEquals(110, recurso.getCantidad());
    }

    @Given("que no tengo ninguna ciudad creada")
    public void noTengoCiudadCreada() {
        recursosReponse = recursoController.obtenerTodosLosRecursos();
        assertTrue(recursosReponse.getBody().isEmpty());
    }

    @When("creo una ciudad llamada {string}")
    public void creoCiudadConNombre(String nombreCiudad) {
        ciudadNombre = nombreCiudad;
        RecursoDto recursoPiedra = new RecursoDto(1L, Tipo_recurso.PIEDRA, 100, 1L);
        recursoCreado = recursoController.crearRecurso(recursoPiedra).getBody();
        assertNotNull(recursoCreado);
    }

    @Then("la ciudad {string} debe tener recursos iniciales asignados")
    public void laCiudadDebeTenerRecursosIniciales(String nombreCiudad) {
        recursosReponse = recursoController.obtenerTodosLosRecursos();
        assertFalse(recursosReponse.getBody().isEmpty());
    }

    @Then("debe tener {int} unidades de {string}")
    public void debeTenerUnidadesDeRecurso(int cantidad, String tipoRecurso) {
        Tipo_recurso tipo = Tipo_recurso.valueOf(tipoRecurso.toUpperCase());
        RecursoDto recurso = recursosReponse.getBody().stream()
                .filter(r -> r.getTipoRecursos().equals(tipo))
                .findFirst()
                .orElse(null);

        assertNotNull(recurso);
        assertEquals(cantidad, recurso.getCantidad());
    }

    @Then("la ciudad {string} debe tener {int} generadores de recursos asignados")
    public void laCiudadDebeTenerGeneradoresAsignados(String nombreCiudad, int cantidadGeneradores) {
        List<Genera_recursoDto> generadores = generaRecursoController.obtenerTodosLosGeneradores().getBody();
        assertEquals(cantidadGeneradores, generadores.size());
    }

    @Then("debe tener un generador de tipo {string} que genera {string}")
    public void debeTenerGeneradorDeTipo(String tipoGenerador, String tipoRecurso) {
        Tipo_generador_recurso tipoGen = Tipo_generador_recurso.valueOf(tipoGenerador.toUpperCase());
        Tipo_recurso tipoRec = Tipo_recurso.valueOf(tipoRecurso.toUpperCase());

        Genera_recursoDto generador = generaRecursoController.obtenerTodosLosGeneradores().getBody().stream()
                .filter(g -> g.getTipoGeneradorRecurso().equals(tipoGen) && g.getTipoRecursoGenerado().equals(tipoRec))
                .findFirst()
                .orElse(null);

        assertNotNull(generador);
    }
}
