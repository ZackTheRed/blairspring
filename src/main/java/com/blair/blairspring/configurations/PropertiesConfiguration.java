package com.blair.blairspring.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:actuator.properties")
@Slf4j
public class PropertiesConfiguration {

    @Value("${management.endpoint.shutdown.enabled}")
    private String shutdownEnabled;

    @Bean
    public String stringBean() {
        log.info("Test property value: {}", shutdownEnabled);
        return shutdownEnabled;
    }

}
