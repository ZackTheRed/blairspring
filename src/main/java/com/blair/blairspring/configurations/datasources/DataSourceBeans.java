package com.blair.blairspring.configurations.datasources;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.lang3.StringUtils;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DataSourceBeans {

    @Autowired
    private Environment env;

    @Profile("ibatis")
    @Bean(initMethod = "init", destroyMethod = "close")
    @Qualifier("applicationDataSource")
    public AtomikosDataSourceBean ibatisTestDbDatasource() throws SQLException {
        final AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        final Properties datasourceProperties = atomikosDataSource.getXaProperties();

        atomikosDataSource.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        atomikosDataSource.setUniqueResourceName("applicationSchemaDatasource");
        atomikosDataSource.setTestQuery("SELECT COUNT(id) FROM teams");
        atomikosDataSource.setPoolSize(10);
        datasourceProperties.setProperty("user", env.getProperty("spring.datasource.username"));
        datasourceProperties.setProperty("password", env.getProperty("spring.datasource.password"));
        datasourceProperties.setProperty("serverName", "localhost");
        datasourceProperties.setProperty("port", "3306");
        datasourceProperties.setProperty("databaseName", "ibatistestdb");
        datasourceProperties.setProperty("pinGlobalTxToPhysicalConnection", "true");

        return atomikosDataSource;
    }

    @Profile("test")
    @Bean(initMethod = "init", destroyMethod = "close")
    @Qualifier("applicationDataSource")
    public AtomikosDataSourceBean embeddedDatasource() throws SQLException {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:testdb" + ";" + "INIT=RUNSCRIPT FROM 'classpath:sql/initialize_embedded-h2.sql'");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("");

        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setXaDataSource(jdbcDataSource);
        atomikosDataSource.setUniqueResourceName("applicationSchemaDatasource");
        return atomikosDataSource;

    }

    /*@Profile("embedded")
    @Bean("ibatis")
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:sql/initialize_embedded-h2.sql")
                .build();
    }*/

    /*@Profile("ibatis")
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
    }*/

}
