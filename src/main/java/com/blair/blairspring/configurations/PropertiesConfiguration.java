package com.blair.blairspring.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
@PropertySource("classpath:actuator.properties")
@PropertySource("classpath:camel.properties")
@PropertySource("classpath:transactions.properties")
@Slf4j
public class PropertiesConfiguration {

    @Value("${management.endpoint.shutdown.enabled}")
    private String shutdownEnabled;

    @Value("#{'kati'.equals('kati')}")
    private Boolean spelProperty;

    @Value("${property1}")
    private String configurerProperty;

    @Bean
    public List<?> testingProperties() {
        log.info("Test property value: {}", shutdownEnabled);
        log.info("SpelProperty: {}", spelProperty);
        log.info("ConfigurerProperty: {}", configurerProperty);
        return List.of(shutdownEnabled, spelProperty, configurerProperty);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource resource = new ClassPathResource("propertySourcesPlaceholderConfigurer.properties");
        configurer.setLocation(resource);
        return configurer;
    }

}
