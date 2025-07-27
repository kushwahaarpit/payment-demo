package com.payment_demo.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/com/payment_demo/features",
    glue = "com.payment_demo.stepdefinition",
    plugin = {"pretty"},
    monochrome = true
)

public class TestRunner {
    
}
