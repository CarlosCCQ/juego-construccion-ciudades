package com.build.api.stepsRecursos;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/resources/features/Recurso/.",
        glue = {"com.build.api.stepsRecursos"},
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class RunCucumberTest {
}