package com.blair.blairspring;

import com.blair.blairspring.util.Lilimpakis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(Lilimpakis.class)
public class BlairspringApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BlairspringApplication.class, args);
        // ((UserCreator) context.getBean("userCreator")).createUsers();
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
