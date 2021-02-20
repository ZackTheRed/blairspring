package com.blair.blairspring.configurations.datasources;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.blair.blairspring.repositories.userschema",
        entityManagerFactoryRef = "userSchemaEntityManagerFactoryBean")
public class UserDataSourceConfiguration {

    @Autowired
    private Environment env;

    @Bean(initMethod = "init", destroyMethod = "close")
    @Primary
    public AtomikosDataSourceBean userSchemaDatasource() throws SQLException {
        final AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        final Properties datasourceProperties = atomikosDataSource.getXaProperties();

        atomikosDataSource.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        atomikosDataSource.setUniqueResourceName("userSchemaDatasource");
        // atomikosDataSource.setTestQuery("SELECT COUNT(id) FROM users");
        atomikosDataSource.setPoolSize(10);
        datasourceProperties.setProperty("user", env.getProperty("user_schema.datasource.username"));
        datasourceProperties.setProperty("password", env.getProperty("user_schema.datasource.password"));
        datasourceProperties.setProperty("serverName", "localhost");
        datasourceProperties.setProperty("port", "3306");
        datasourceProperties.setProperty("databaseName", "user_schema");
        datasourceProperties.setProperty("pinGlobalTxToPhysicalConnection", "true");

        return atomikosDataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userSchemaEntityManagerFactoryBean() throws SQLException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJtaDataSource(userSchemaDatasource());
        factoryBean.setPackagesToScan("com.blair.blairspring.model.userschema");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.current_session_context_class", "jta");
        jpaProperties.put("hibernate.transaction.factory_class", "com.atomikos.icatch.jta.hibernate3.AtomikosJTATransactionFactory");
        jpaProperties.put("hibernate.transaction.manager_lookup_class", "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.dialect.storage_engine", "innodb");

        factoryBean.setJpaProperties(jpaProperties);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    /*@Primary
    @Bean*/
    /*public DataSource userSchemaDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(env.getProperty("spring.user-schema-datasource.url"));
        cpds.setUser(env.getProperty("spring.user-schema-datasource.username"));
        cpds.setPassword(env.getProperty("spring.user-schema-datasource.password"));

        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds;
    }*/

    /*@Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean userSchemaEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userSchemaDataSource());
        em.setPackagesToScan(new String[]{"com.blair.blairspring.model.userschema"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
        em.setJpaPropertyMap(properties);
        return em;
    }*/

    /*@Primary
    @Bean
    public PlatformTransactionManager userSchemaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(userSchemaEntityManager().getObject());
        return transactionManager;
    }*/

}
