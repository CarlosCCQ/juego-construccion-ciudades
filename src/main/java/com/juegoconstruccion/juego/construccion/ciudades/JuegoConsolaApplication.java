package com.juegoconstruccion.juego.construccion.ciudades;

import com.juegoconstruccion.juego.construccion.ciudades.gamescontroller.MainGameController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Clase principal que arranca la aplicación de consola del juego.
 * Configura los beans necesarios para la ejecución del juego.
 */
@SpringBootApplication
public class JuegoConsolaApplication {

	/**
	 * Método principal que inicia la aplicación.
	 *
	 * @param args argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(JuegoConsolaApplication.class, args);
	}

	/**
	 * Bean para inicializar el juego al inicio de la aplicacion
	 *
	 * @param, usado para obtener el controlador del juego
	 *
	 * @return un CommandLineRunner que ejecuta el juego
	 * */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext aplicationContext){
		return args -> {
			MainGameController gameController = aplicationContext.getBean(MainGameController.class);
			gameController.startGame();
		};
	}

	/**
	 * Bean que proporciona un executor programado.
	 *
	 * @return un ScheduledExecutorService para ejecutar tareas programadas.
	 */
	@Bean
	public ScheduledExecutorService scheduledExecutorService(){
		return Executors.newScheduledThreadPool(1);
	}
}
