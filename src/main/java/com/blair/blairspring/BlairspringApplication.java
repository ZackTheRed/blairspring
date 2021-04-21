package com.blair.blairspring;

import com.blair.blairspring.util.Lilimpakis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(Lilimpakis.class)
public class BlairspringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BlairspringApplication.class, args);
        context.getApplicationName();
    }

}
