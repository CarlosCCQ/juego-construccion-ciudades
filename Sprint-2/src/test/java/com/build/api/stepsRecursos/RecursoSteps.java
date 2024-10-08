package com.build.api.stepsRecursos;

import com.build.api.controller.Genera_recursoController;
import com.build.api.controller.RecursoController;
import com.build.api.dto.CiudadDto;
import com.build.api.dto.Genera_recursoDto;
import com.build.api.dto.RecursoDto;
import com.build.api.model.generador.Tipo_generador_recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.service.ciudadservice.CiudadService;
import com.build.api.service.recursoservice.RecursoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.junit.Assert.*;

@SpringBootTest
public class RecursoSteps {
    @Autowired
    private RecursoService recursoService;

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private GeneradorService generadorService; // Servicio para gestionar generadores

    private Long ciudadId; // ID de la ciudad
    private String ciudadNombre; // Nombre de la ciudad

    @Given("que no tengo ninguna ciudad creada")
    public void que_no_tengo_ninguna_ciudad_creada() {
        // Lógica para asegurarse de que no hay ciudades creadas
        List<CiudadDto> ciudades = ciudadService.obtenerTodasLasCiudades();
        assertEquals("Debería estar vacío", 0, ciudades.size());
    }

    @When("creo una ciudad llamada {string}")
    public void creo_una_ciudad_llamada(String ciudadNombre) {
        this.ciudadNombre = ciudadNombre;
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setNombre(ciudadNombre);
        ciudadId = ciudadService.crearCiudad(ciudadDto).getId(); // Supongamos que este método devuelve el ID de la ciudad creada
    }

    @Then("la ciudad {string} debe tener recursos iniciales asignados")
    public void la_ciudad_debe_tener_recursos_iniciales_asignados(String ciudadNombre) {
        List<RecursoDto> recursos = recursoService.obtenerRecursosPorCiudad(ciudadId);
        // Verifica que la ciudad tenga recursos iniciales (puedes ajustar los recursos esperados según tu lógica)
        assertEquals("La ciudad debe tener recursos iniciales", 3, recursos.size());
    }

    @Then("debe tener {int} unidades de {string}")
    public void debe_tener_unidades_de(int cantidadEsperada, String tipoRecursoString) {
        List<RecursoDto> recursos = recursoService.obtenerRecursosPorCiudad(ciudadId);
        RecursoDto recursoActual = recursos.stream()
                .filter(r -> r.getTipoRecursos().name().equalsIgnoreCase(tipoRecursoString))
                .findFirst()
                .orElse(null);

        assertEquals("La cantidad de recurso " + tipoRecursoString + " no es la esperada", cantidadEsperada, recursoActual.getCantidad());
    }

    @Given("el jugador tiene generadores de recursos asignados a su ciudad")
    public void el_jugador_tiene_generadores_de_recursos_asignados_a_su_ciudad() {
        // Lógica para asignar generadores de recursos a la ciudad creada
        generadorService.asignarGeneradores(ciudadId);
    }

    @When("el jugador decide generar recursos manualmente")
    public void el_jugador_decide_generar_recursos_manualmente() {
        // Lógica para generar recursos manualmente
        recursoService.generarRecursosManual(ciudadId);
    }

    @Then("los recursos se añaden a los recursos actuales de la ciudad")
    public void los_recursos_se_añaden_a_los_recursos_actuales_de_la_ciudad() {
        List<RecursoDto> recursos = recursoService.obtenerRecursosPorCiudad(ciudadId);
        // Verifica que la cantidad de recursos haya aumentado según la generación manual
        // Aquí necesitarás lógica específica para verificar que la cantidad aumentó correctamente
    }

    @Given("una ciudad tiene generadores de recursos activos")
    public void una_ciudad_tiene_generadores_de_recursos_activos() {
        // Lógica para activar generadores de recursos si no están ya activos
        generadorService.activarGeneradores(ciudadId);
    }

    @When("el tiempo programado de generación de recursos se cumple")
    public void el_tiempo_programado_de_generacion_de_recursos_se_cumple() {
        // Simulación del paso del tiempo para que se ejecuten los generadores
        generadorService.generarRecursosAutomaticamente(ciudadId);
    }

    @Then("los generadores de recursos añaden más recursos a la ciudad")
    public void los_generadores_de_recursos_añaden_mas_recursos_a_la_ciudad() {
        // Verifica que los recursos se han añadido a la ciudad
        List<RecursoDto> recursos = recursoService.obtenerRecursosPorCiudad(ciudadId);
        // Aquí necesitas lógica específica para comprobar que los recursos han aumentado
    }

    @Then("la ciudad {string} debe tener {int} generadores de recursos asignados")
    public void la_ciudad_debe_tener_generadores_de_recursos_asignados(String ciudadNombre, int cantidadEsperada) {
        List<Genera_recursoDto> generadores = generadorService.obtenerGeneradoresPorCiudad(ciudadId);
        assertEquals("La ciudad debe tener " + cantidadEsperada + " generadores de recursos", cantidadEsperada, generadores.size());
    }

    @Then("debe tener un generador de tipo {string} que genera {string}")
    public void debe_tener_un_generador_de_tipo_que_genera(String tipoGeneradorString, String tipoRecursoString) {
        List<Genera_recursoDto> generadores = generadorService.obtenerGeneradoresPorCiudad(ciudadId);
        boolean generadorExists = generadores.stream()
                .anyMatch(g -> g.getTipoGenerador().name().equalsIgnoreCase(tipoGeneradorString) && g.getTipoRecursoGenerado().name().equalsIgnoreCase(tipoRecursoString));

        assertEquals("El generador de tipo " + tipoGeneradorString + " que genera " + tipoRecursoString + " no existe", true, generadorExists);
    }
}
}
