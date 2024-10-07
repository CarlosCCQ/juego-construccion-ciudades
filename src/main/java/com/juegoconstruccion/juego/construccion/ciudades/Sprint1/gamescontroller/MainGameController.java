package com.juegoconstruccion.juego.construccion.ciudades.Sprint1.gamescontroller;

import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.dto.CiudadDto;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.dto.EdificioDto;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.dto.Genera_recursoDto;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.Tipo_generador_recurso;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.Tipo_recurso;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.model.edificio.Tipo_edificio;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.service.CiudadService;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.service.Genera_recursoService;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.service.RecursoService;
import com.juegoconstruccion.juego.construccion.ciudades.Sprint1.service.edificio.EdificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class MainGameController {
    private final CiudadService ciudadService;
    private final EdificioService edificioService;
    private final Genera_recursoService generaRecursoService;
    private final RecursoService recursoService;
    private final ScheduledExecutorService scheduler;

    @Autowired
    public MainGameController(CiudadService ciudadService, EdificioService edificioService, Genera_recursoService generaRecursoService, RecursoService recursoService, ScheduledExecutorService scheduler){
        this.ciudadService = ciudadService;
        this.edificioService = edificioService;
        this.generaRecursoService = generaRecursoService;
        this.recursoService = recursoService;
        this.scheduler = scheduler;
    }

    public void startGame(){
        iniciarGeneracionAutomaticaRecursos();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running){
            System.out.println("\n** Menú del juego **\n");
            System.out.println("1. Crear ciudades");
            System.out.println("2. Ver ciudades");
            System.out.println("3. Construir edificios");
            System.out.println("4. Generar recursos");
            System.out.println("5. Ver estados de la ciudad");
            System.out.println("6. Salir");
            System.out.println("Elige una opción");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion){
                case 1:
                    crearCiudad(scanner);
                    break;
                case 2:
                    mostrarCiudades();
                    break;
                case 3:
                    construirEdificio(scanner);
                    break;
                case 4:
                    generarRecursos(scanner);
                    break;
                case 5:
                    verEstadoCiudad(scanner);
                    break;
                case 6:
                    running = false;
                    System.out.println("Gracias por jugar!");
                    detenerGeneracionAutomaticaRecursos();
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
        scanner.close();
    }

    private void iniciarGeneracionAutomaticaRecursos() {
        Runnable generarRecursosTask = () -> {
            List<CiudadDto> ciudades = ciudadService.obtenerTodasLasCiudades();

            for (CiudadDto ciudad : ciudades) {
                List<Genera_recursoDto> generadores = generaRecursoService.obtenerGeneradoresPorCiudad(ciudad.getId());

                for (Genera_recursoDto generador : generadores) {
                    int cantidadGenerada = new Random().nextInt(4) + 2;
                    recursoService.aumentarRecurso(ciudad.getId(), generador.getTipoRecursoGenerado(), cantidadGenerada);
                    System.out.println("Se generaron " + cantidadGenerada + " unidades de " + generador.getTipoRecursoGenerado() + " en la ciudad " + ciudad.getNombre());
                }
            }
        };

        scheduler.scheduleAtFixedRate(generarRecursosTask, 30, 30, TimeUnit.SECONDS);
    }

    private void detenerGeneracionAutomaticaRecursos() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

    private void crearCiudad(Scanner scanner){
        System.out.println("Ingrese el nombre de la ciudad: ");
        String nombreCiudad = scanner.nextLine();

        CiudadDto ciudadDto = new CiudadDto();
        ciudadDto.setNombre(nombreCiudad);

        CiudadDto nuevaCiudad = ciudadService.crearCiudad(ciudadDto);

        Genera_recursoDto cantera = new Genera_recursoDto(Tipo_generador_recurso.CANTERAS, nuevaCiudad.getId(), Tipo_recurso.PIEDRA);
        Genera_recursoDto mina = new Genera_recursoDto(Tipo_generador_recurso.MINAS, nuevaCiudad.getId(), Tipo_recurso.ORO);
        Genera_recursoDto rio = new Genera_recursoDto(Tipo_generador_recurso.RIO, nuevaCiudad.getId(), Tipo_recurso.AGUA);

        generaRecursoService.crearGenerador(cantera);
        generaRecursoService.crearGenerador(mina);
        generaRecursoService.crearGenerador(rio);

        System.out.println("Ciudad creada: " + nuevaCiudad.getNombre() + " (ID: " + nuevaCiudad.getId() + ")");
        System.out.println("Se han asignado los siguientes generadores de recursos:");
        System.out.println("1. CANTERA -> PIEDRA");
        System.out.println("2. MINA -> ORO");
        System.out.println("3. RIO -> AGUA");
    }

    private void mostrarCiudades(){
        List<CiudadDto> ciudades = ciudadService.obtenerTodasLasCiudades();
        System.out.println("\n** Ciudades **");
        if(ciudades.isEmpty()){
            System.out.println("No hay ciudades creadas aún");
        } else {
            for(CiudadDto ciudad : ciudades){
                System.out.println("ID: " + ciudad.getId() + " | Nombre: " + ciudad.getNombre());
            }
        }
    }

    private void construirEdificio(Scanner scanner) {
        crearCiudadSiNoExiste(scanner);

        Long ciudadId = seleccionarCiudad(scanner);
        if (ciudadId == null) {
            System.out.println("No se seleccionó ninguna ciudad. Cancelando construcción.");
            return;
        }

        System.out.println("\n** Selecciona el tipo de edificio **");
        System.out.println("1. Edificio de Clase Alta");
        System.out.println("2. Edificio de Clase Media");
        System.out.println("3. Edificio de Clase Baja");
        System.out.println("4. Salir de la construcción de edificios");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        String nombreEdificio = null;
        Map<Tipo_recurso, Integer> costoEdificio = new HashMap<>();
        Tipo_edificio tipoEdificio = null;

        switch (opcion) {
            case 1:
                nombreEdificio = "Edificio de Clase Alta";
                costoEdificio.put(Tipo_recurso.PIEDRA, 10);
                costoEdificio.put(Tipo_recurso.ORO, 5);
                costoEdificio.put(Tipo_recurso.AGUA, 3);
                tipoEdificio = Tipo_edificio.CLASE_ALTA;
                break;
            case 2:
                nombreEdificio = "Edificio de Clase Media";
                costoEdificio.put(Tipo_recurso.PIEDRA, 7);
                costoEdificio.put(Tipo_recurso.ORO, 4);
                costoEdificio.put(Tipo_recurso.AGUA, 2);
                tipoEdificio = Tipo_edificio.CLASE_MEDIA;
                break;
            case 3:
                nombreEdificio = "Edificio de Clase Baja";
                costoEdificio.put(Tipo_recurso.PIEDRA, 5);
                costoEdificio.put(Tipo_recurso.ORO, 2);
                costoEdificio.put(Tipo_recurso.AGUA, 1);
                tipoEdificio = Tipo_edificio.CLASE_BAJA;
                break;
            case 4:
                System.out.println("Saliendo de la construcción de edificios.");
                return;
            default:
                System.out.println("Opción no válida. Inténtalo de nuevo.");
                return;
        }

        if (!edificioService.verificarRecursosParaConstruir(ciudadId, costoEdificio)) {
            System.out.println("Recursos no suficientes. Puedes esperar a que tus generadores de recursos generen más recursos o elegir otro edificio.");
            return;
        }

        EdificioDto nuevoEdificio = edificioService.crearEdificio(new EdificioDto(nombreEdificio, ciudadId, tipoEdificio, costoEdificio));
        System.out.println("Edificio construido: " + nuevoEdificio.getNombre());
    }

    private Long seleccionarCiudad(Scanner scanner) {
        List<CiudadDto> ciudades = ciudadService.obtenerTodasLasCiudades();
        System.out.println("\n** Ciudades Disponibles **");
        for (CiudadDto ciudad : ciudades) {
            System.out.println("ID: " + ciudad.getId() + " | Nombre: " + ciudad.getNombre());
        }

        System.out.println("Ingrese el ID de la ciudad donde desea construir el edificio: ");
        return scanner.nextLong();
    }

    private void crearCiudadSiNoExiste(Scanner scanner) {
        List<CiudadDto> ciudades = ciudadService.obtenerTodasLasCiudades();
        if (ciudades.isEmpty()) {
            System.out.println("No hay ciudades creadas. Creando una nueva ciudad.");
            crearCiudad(scanner);
        } else {
            System.out.println("Ya hay ciudades creadas. Puedes elegir una existente para construir edificios.");
        }
    }

    private void generarRecursos(Scanner scanner){
        System.out.println("Ingrese el ID de la ciudad donde quiere generar recursos: ");
        Long ciudadId = scanner.nextLong();

        List<Genera_recursoDto> generadores = generaRecursoService.obtenerGeneradoresPorCiudad(ciudadId);

        if (generadores.isEmpty()) {
            System.out.println("La ciudad no tiene generadores de recursos.");
            return;
        }

        System.out.println("\n** Generadores en la Ciudad **");
        for (Genera_recursoDto generador : generadores) {
            System.out.println("Tipo Generador: " + generador.getTipoGeneradorRecurso() + " | Recurso Generado: " + generador.getTipoRecursoGenerado());
        }

        System.out.println("Ingrese el ID del generador de recursos a utilizar: ");
        Long generadorId = scanner.nextLong();

        Genera_recursoDto generadorSeleccionado = generaRecursoService.obtenerGeneradorPorId(generadorId);
        System.out.println("Generando " + generadorSeleccionado.getCapacidadGeneracion() + " de " + generadorSeleccionado.getTipoRecursoGenerado() + "...");
    }

    private void verEstadoCiudad(Scanner scanner){
        String nombreCiudad = "";
        CiudadDto ciudad = null;

        while (ciudad == null) {
            System.out.println("Ingrese el nombre de la ciudad:");
            nombreCiudad = scanner.nextLine();

            ciudad = ciudadService.obtenerCiudadPorNombre(nombreCiudad);

            if (ciudad == null) {
                System.out.println("Ciudad no encontrada. Por favor, ingresa un nombre válido.");
            }
        }

        Map<Tipo_recurso, Integer> recursos = ciudadService.obtenerRecursoDeLaCiudad(nombreCiudad);

        System.out.println("\n** Estado de la Ciudad **");
        System.out.println("Nombre: " + ciudad.getNombre());
        System.out.println("Edificios: " + ciudad.getEdificioIds().size());
        System.out.println("Generadores de Recursos: " + ciudad.getGeneraRecursoIds().size());

        System.out.println("Recursos: ");
        for (Map.Entry<Tipo_recurso, Integer> entry : recursos.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
