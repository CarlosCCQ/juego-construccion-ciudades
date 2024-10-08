package com.build.api.steps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/resources/features",
        glue = {"com.build.api.steps"},
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)
public class RunCucumberTest {
}
