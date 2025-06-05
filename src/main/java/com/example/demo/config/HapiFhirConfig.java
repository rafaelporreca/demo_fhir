package com.example.demo.config;

import ca.uhn.fhir.jpa.model.entity.StorageSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HapiFhirConfig {

    @Bean
    public StorageSettings storageSettings() {
        return new StorageSettings();
    }
}
