package com.construccion.ciudades.construccion;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.construccion.ciudades.construccion.steps"
)
public class CucumberTest {
}
