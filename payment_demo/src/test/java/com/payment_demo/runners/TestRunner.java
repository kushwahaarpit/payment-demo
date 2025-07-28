package com.payment_demo.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/com/payment_demo/features",
    glue = "com.payment_demo.stepDefination",
    plugin = {"pretty", "html:target/cucumber.html"},
    monochrome = true
)

public class TestRunner {
    
}
