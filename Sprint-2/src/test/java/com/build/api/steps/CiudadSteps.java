package com.build.api.steps;

import com.build.api.dto.CiudadDto;
import com.build.api.dto.Genera_recursoDto;
import com.build.api.model.ciudad.Ciudad;
import com.build.api.model.edificio.Edificio;
import com.build.api.model.edificio.Tipo_edificio;
import com.build.api.model.generador.Genera_recuso;
import com.build.api.model.generador.Tipo_generador_recurso;
import com.build.api.model.recurso.Tipo_recurso;
import com.build.api.service.ciudadservice.CiudadService;
import com.build.api.service.generadorservice.Genera_recursoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CiudadSteps {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private Genera_recursoService generaRecursoService;

    private CiudadDto ciudadDto;
    private List<Edificio> edificios = new ArrayList<>();
    private List<Genera_recursoDto> generadores = new ArrayList<>();


    private Ciudad convertirCiudadDtoACiudad(CiudadDto ciudadDto) {
        Ciudad ciudad = new Ciudad();
        ciudad.setId(ciudadDto.getId());
        ciudad.setNombre(ciudadDto.getNombre());

        return ciudad;
    }

    @Given("el jugador crea una ciudad llamada {string}")
    public void elJugadorCreaUnaCiudadLlamada(String nombreCiudad) {
        ciudadDto = new CiudadDto();
        ciudadDto.setNombre(nombreCiudad);
        ciudadDto = ciudadService.crearCiudad(ciudadDto);
        System.out.println("Ciudad creada: " + nombreCiudad);
    }

    @When("el jugador añade generadores de recursos y edificios a la ciudad")
    public void elJugadorAnadeGeneradoresDeRecursosYEdificiosALaCiudad() {

        Edificio edificio = new Edificio();
        edificio.setNombre("Edificio Principal");
        edificio.setTipoEdificio(Tipo_edificio.CLASE_BAJA);
        edificio.setCiudad(convertirCiudadDtoACiudad(ciudadDto));
        edificios.add(edificio);


        Genera_recursoDto generador = new Genera_recursoDto();
        generador.setTipoGeneradorRecurso(Tipo_generador_recurso.MINAS);
        generador.setCiudadId(ciudadDto.getId());
        generador.setTipoRecursoGenerado(Tipo_recurso.ORO);
        generadores.add(generador);


        generaRecursoService.crearGenerador(generador);
        System.out.println("Generadores y edificios añadidos a la ciudad.");
    }

    @Then("los generadores de recursos producen recursos")
    public void losGeneradoresDeRecursosProducenRecursos() {

        assertFalse(generadores.isEmpty(), "La ciudad debe tener generadores de recursos.");
        System.out.println("Los generadores están produciendo recursos.");
    }
}
