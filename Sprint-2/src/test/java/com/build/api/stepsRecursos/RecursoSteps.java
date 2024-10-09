package com.build.api.stepsRecursos;

import com.build.api.controller.Genera_recursoController;
import com.build.api.controller.RecursoController;
import com.build.api.dto.CiudadDto;
import com.build.api.dto.Genera_recursoDto;
import com.build.api.dto.RecursoDto;
import com.build.api.model.generador.Tipo_generador_recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.service.ciudadservice.CiudadService;
import com.build.api.service.generadorservice.Genera_recursoService;
import com.build.api.service.recursoservice.RecursoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.junit.Assert.*;

@SpringBootTest
public class RecursoSteps {
    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private Genera_recursoService generaRecursoService;

    private Long ciudadId;

    @Given("que no tengo ninguna ciudad creada")
    public void que_no_tengo_ninguna_ciudad_creada() {
        // Asegurarse de que no hay ciudades en la base de datos
        List<CiudadDto> ciudades = ciudadService.obtenerTodasLasCiudades();
        Assert.assertTrue(ciudades.isEmpty());
    }

    @When("creo una ciudad llamada {string}")
    public void creo_una_ciudad_llamada(String nombreCiudad) {
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setNombre(nombreCiudad);
        ciudadDto.setRecursosIniciales(100); // o establece tus recursos iniciales aquí

        ciudadId = ciudadService.crearCiudad(ciudadDto).getId(); // Guarda el ID de la ciudad creada
    }

    @Then("la ciudad {string} debe tener recursos iniciales asignados")
    public void la_ciudad_debe_tener_recursos_iniciales_asignados(String nombreCiudad) {
        CiudadDto ciudad = ciudadService.obtenerCiudadPorId(ciudadId);
        Assert.assertNotNull(ciudad);
        Assert.assertEquals(nombreCiudad, ciudad.getNombre());

        // Verificar los recursos iniciales
        List<RecursoDto> recursos = recursoService.obtenerTodosLosRecursos();
        Assert.assertEquals(1, recursos.size()); // Asegúrate de que solo haya un recurso asignado
    }

    @Then("debe tener {int} unidades de {string}")
    public void debe_tener_unidades_de(int cantidad, String tipoRecurso) {
        RecursoDto recurso = recursoService.obtenerRecursoPorTipoYCiudad(tipoRecurso, ciudadId);
        Assert.assertNotNull(recurso);
        Assert.assertEquals(cantidad, recurso.getCantidad());
    }

    @Given("el jugador tiene generadores de recursos asignados a su ciudad")
    public void el_jugador_tiene_generadores_de_recursos_asignados_a_su_ciudad() {
        // Crear generadores para la ciudad existente
        Genera_recursoDto generaRecursoDto = new Genera_recursoDto();
        generaRecursoDto.setTipoGeneradorRecurso(Tipo_generador_recurso.CANTERAS);
        generaRecursoDto.setTipoRecursoGenerado(Tipo_recurso.PIEDRA);
        generaRecursoDto.setCapacidadGeneracion(5);
        generaRecursoDto.setCiudadId(ciudadId);
        generaRecursoService.crearGenerador(generaRecursoDto);

        // Repite para otros generadores (MINAS y RIO)
    }

    @When("el jugador decide generar recursos manualmente")
    public void el_jugador_decide_generar_recursos_manual() {
        // Lógica para generar recursos manualmente (ej. llamar al método adecuado en el servicio)
        recursoService.aumentarRecurso(ciudadId, Tipo_recurso.PIEDRA, 5);
    }

    @Then("los recursos se añaden a los recursos actuales de la ciudad")
    public void los_recursos_se_añaden_a_los_recursos_actuales_de_la_ciudad() {
        RecursoDto recurso = recursoService.obtenerRecursoPorTipoYCiudad(Tipo_recurso.PIEDRA, ciudadId);
        Assert.assertNotNull(recurso);
        Assert.assertTrue(recurso.getCantidad() > 100); // Verifica que la cantidad se haya incrementado
    }

    @Given("una ciudad tiene generadores de recursos activos")
    public void una_ciudad_tiene_generadores_de_recursos_activos() {
        // Verificar que la ciudad tiene generadores activos (puedes usar el paso anterior)
        List<Genera_recursoDto> generadores = generaRecursoService.obtenerGeneradoresPorCiudad(ciudadId);
        Assert.assertFalse(generadores.isEmpty());
    }

    @When("el tiempo programado de generación de recursos se cumple")
    public void el_tiempo_programado_de_generacion_de_recursos_se_cumple() {
        // Simula la lógica de tiempo, tal vez llamando a un método en el servicio que "avance" el tiempo
        // Por simplicidad, podrías hacer una llamada directa a un método que aumente los recursos
        generaRecursoService.generarRecursosAutomáticamente(ciudadId);
    }

    @Then("los generadores de recursos añaden más recursos a la ciudad")
    public void los_generadores_de_recursos_añaden_mas_recursos_a_la_ciudad() {
        RecursoDto recurso = recursoService.obtenerRecursoPorTipoYCiudad(Tipo_recurso.PIEDRA, ciudadId);
        Assert.assertTrue(recurso.getCantidad() > 105); // Verifica que la cantidad se haya incrementado por la generación automática
    }

    @Given("creo una ciudad llamada {string}")
    public void creo_una_ciudad_llamada_generadora(String nombreCiudad) {
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setNombre(nombreCiudad);
        ciudadId = ciudadService.crearCiudad(ciudadDto).getId();
    }

    @Then("la ciudad {string} debe tener {int} generadores de recursos asignados")
    public void la_ciudad_debe_tener_generadores_de_recursos_asignados(String nombreCiudad, int cantidadGeneradores) {
        List<Genera_recursoDto> generadores = generaRecursoService.obtenerGeneradoresPorCiudad(ciudadId);
        Assert.assertEquals(cantidadGeneradores, generadores.size());
    }

    @Then("debe tener un generador de tipo {string} que genera {string}")
    public void debe_tener_un_generador_de_tipo_que_genera(String tipoGenerador, String tipoRecurso) {
        List<Genera_recursoDto> generadores = generaRecursoService.obtenerGeneradoresPorCiudad(ciudadId);
        boolean encontrado = generadores.stream()
                .anyMatch(g -> g.getTipoGeneradorRecurso().name().equals(tipoGenerador) &&
                        g.getTipoRecursoGenerado().name().equals(tipoRecurso));
        Assert.assertTrue("No se encontró el generador esperado.", encontrado);
    }
}

