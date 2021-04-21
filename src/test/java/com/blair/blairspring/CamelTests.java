package com.blair.blairspring;

import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.CamelTestContextBootstrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@CamelSpringBootTest
//@SpringBootTest
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration
public class CamelTests {

    @Autowired
    private CamelContext camelContext;

    @Configuration
    @ComponentScan("com.blair.blairspring.camel")
    public static class SpringConfig {

    }

    @Test
    void assertCamelContextIsNotNull() {
        assertNotNull(camelContext);
    }


}
