package com.example.various;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = "com.example.*")
@SpringBootApplication
public class VariousConceptsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VariousConceptsApplication.class, args);
    }
}
