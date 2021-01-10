package com.blair.blairspring.configurations.datasources;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DataSourceBeans {

    @Autowired
    private Environment env;

    @Profile("embedded")
    @Bean("ibatis")
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/initialize-embedded.sql")
                .build();
    }

    @Profile("ibatis")
    @Bean("ibatis")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource ibatisSchemaDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(env.getProperty("spring.datasource.url"));
        cpds.setUser(env.getProperty("spring.datasource.username"));
        cpds.setPassword(env.getProperty("spring.datasource.password"));

        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds;
    }

}
