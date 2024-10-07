package com.juegoconstruccion.juego.construccion.ciudades;

import com.juegoconstruccion.juego.construccion.ciudades.gamescontroller.MainGameController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
public class JuegoConsolaApplication {
	public static void main(String[] args) {
		SpringApplication.run(JuegoConsolaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext aplicationContext){
		return args -> {
			MainGameController gameController = aplicationContext.getBean(MainGameController.class);
			gameController.startGame();
		};
	}

	@Bean
	public ScheduledExecutorService scheduledExecutorService(){
		return Executors.newScheduledThreadPool(1);
	}
}
