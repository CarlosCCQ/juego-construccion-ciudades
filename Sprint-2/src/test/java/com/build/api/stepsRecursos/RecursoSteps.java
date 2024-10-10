package com.build.api.stepsRecursos;

import com.build.api.controller.Genera_recursoController;
import com.build.api.controller.RecursoController;
import com.build.api.dto.CiudadDto;
import com.build.api.dto.Genera_recursoDto;
import com.build.api.dto.RecursoDto;
import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.generador.Genera_recuso;
import com.build.api.model.generador.Tipo_generador_recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.repository.CiudadRepository;
import com.build.api.repository.GeneraRecursoRepository;
import com.build.api.repository.RecursoRepository;
import com.build.api.service.ciudadservice.CiudadService;
import com.build.api.service.generadorservice.Genera_recursoService;
import com.build.api.service.recursoservice.RecursoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private GeneraRecursoRepository generaRecursoRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    Long ciudadId1, ciudadId2, ciudadId3;

    CiudadDto ciudad1, ciudad2, ciudad3;

    @Before
    public void setUp() {
        ciudadService.eliminarTodasLasCiudades();
    }

    @Given("que no tengo ninguna ciudad creada")
    public void que_no_tengo_ninguna_ciudad_creada() {
        List<CiudadDto> ciudades = ciudadService.obtenerTodasLasCiudades();
        Assert.assertTrue(ciudades.isEmpty());
    }

    @When("creo una ciudad llamada {string}")
    public void creo_una_ciudad_llamada(String nombreCiudad) {
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setNombre(nombreCiudad);
        ciudadDto.setRecursosIniciales(100);

        ciudadId1 = ciudadService.crearCiudad(ciudadDto).getId();
        ciudad1 = ciudadService.obtenerCiudadPorId(ciudadId1);
    }

    @Then("la ciudad {string} debe tener recursos iniciales asignados")
    public void la_ciudad_debe_tener_recursos_iniciales_asignados(String nombreCiudad) {
        Assert.assertNotNull(ciudad1);
        Assert.assertEquals(nombreCiudad, ciudad1.getNombre());

        List<RecursoDto> recursos = recursoService.obtenerTodosLosRecursos();
        Assert.assertEquals(3, recursos.size());
    }

    @Then("debe tener {int} unidades de {string}")
    public void debe_tener_unidades_de(int cantidad, String tipoRecursoStr) {
        Tipo_recurso tipoRecurso = Tipo_recurso.valueOf(tipoRecursoStr.toUpperCase());
        RecursoDto recurso = recursoService.obtenerRecursoPorTipoYCiudad(tipoRecurso, ciudadId1);
        Assert.assertNotNull(recurso);
        Assert.assertEquals(cantidad, recurso.getCantidad());
    }

    @Given("que tengo una ciudad llamada {string} con generadores de recursos asignados")
    public void una_ciudad_tiene_generadores_de_recursos_activos(String nombre) {

        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setNombre(nombre);
        ciudadDto.setRecursosIniciales(100);
        ciudadId2 = ciudadService.crearCiudad(ciudadDto).getId();
        Ciudad ciudad = ciudadRepository.findById(ciudadId2).orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));
        ciudadService.asignarGeneradorDeRecursos(ciudad);
    }

    @When("el tiempo programado de {int} segundos se cumple")
    public void el_tiempo_programado_de_generacion_de_recursos_se_cumple(int tiempo) {
        try {
            Thread.sleep(tiempo * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("El hilo fue interrumpido");
        }
    }

    @Then("los generadores de recursos añaden más recursos a la ciudad")
    public void los_generadores_de_recursos_añaden_mas_recursos_a_la_ciudad() {
        List<RecursoDto> listaRecursosDespues = recursoService.obtenerTodosLosRecursos();
        boolean recursoAumentado = false;
        for (RecursoDto recurso : listaRecursosDespues) {
            System.out.println("Recurso ID: " + recurso.getId());
            System.out.println("Tipo: " + recurso.getTipoRecursos());
            System.out.println("Cantidad: " + recurso.getCantidad());
            System.out.println("-----------------------------");

            if (recurso.getCantidad() > 100) {
                recursoAumentado = true;
            }
        }
        Assert.assertTrue("Ningún recurso ha aumentado más de 100", recursoAumentado);
    }

    @Given("creo una ciudad {string}")
    public void creo_una_ciudad_generadora_llamada(String nombreCiudad) {
        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setNombre(nombreCiudad);
        ciudadDto.setRecursosIniciales(100);
        ciudadId3 = ciudadService.crearCiudad(ciudadDto).getId();
        Ciudad ciudad = ciudadRepository.findById(ciudadId3).orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada"));
        ciudadService.asignarGeneradorDeRecursos(ciudad);
    }

    @Then("la ciudad debe tener {int} generadores de recursos asignados")
    public void la_ciudad_debe_tener_generadores_de_recursos_asignados(int cantidadGeneradores) {
        List<Genera_recursoDto> generadores = generaRecursoService.obtenerGeneradoresPorCiudad(ciudadId3);
        System.out.println("Cantidad de generadores asignados: " + generadores.size());
        Assert.assertEquals(cantidadGeneradores, generadores.size());
    }

    @Then("debe tener un generador de tipo {string} que genera {string}")
    public void debe_tener_un_generador_de_tipo_que_genera(String tipoGenerador, String tipoRecurso) {
        List<Genera_recursoDto> generadores = generaRecursoService.obtenerGeneradoresPorCiudad(ciudadId3);
        boolean encontrado = generadores.stream()
                .anyMatch(g -> g.getTipoGeneradorRecurso().name().equals(tipoGenerador) &&
                        g.getTipoRecursoGenerado().name().equals(tipoRecurso));
        Assert.assertTrue("No se encontró el generador esperado.", encontrado);
    }
}

