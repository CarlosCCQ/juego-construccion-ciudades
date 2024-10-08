package com.build.api.steps;

import com.build.api.Sprint2Application;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {Sprint2Application.class})// Reemplaza Application.class por la clase principal de tu proyecto Spring Boot

public class CucumberSpringConfiguration {
}