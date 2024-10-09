package com.build.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class AppConfig {

    @Bean
    public ScheduledExecutorService taskScheduler() {
        // Puedes configurar el tamaño del pool de hilos según tus necesidades
        return Executors.newScheduledThreadPool(10);
    }
}
