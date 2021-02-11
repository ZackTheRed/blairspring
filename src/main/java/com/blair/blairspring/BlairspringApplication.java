package com.blair.blairspring;

import com.blair.blairspring.util.Lilimpakis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(Lilimpakis.class)
public class BlairspringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BlairspringApplication.class, args);
        // context.registerShutdownHook();
        // context.close();
    }

    /*public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(BlairspringApplication.class);
        ConfigurableApplicationContext context = builder.run(args);
    }*/

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlairspringApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
    }*/
}
