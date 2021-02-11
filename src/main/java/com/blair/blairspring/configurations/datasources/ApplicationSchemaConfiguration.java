package com.blair.blairspring.configurations.datasources;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.blair.blairspring.repositories.ibatisschema",
        entityManagerFactoryRef = "applicationEntityManager")
@Slf4j
public class ApplicationSchemaConfiguration {

    private final Environment env;

    // @Resource(name = "ibatis")
    // private DataSource dataSource;

    private final AtomikosDataSourceBean atomikosDataSourceBean;

    public ApplicationSchemaConfiguration(
            Environment env,
            @Qualifier("applicationDataSource") AtomikosDataSourceBean atomikosDataSourceBean) {
        this.env = env;
        this.atomikosDataSourceBean = atomikosDataSourceBean;
    }

    @Bean
    public EntityManagerFactory applicationEntityManager() throws SQLException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJtaDataSource(atomikosDataSourceBean);
        factoryBean.setPackagesToScan("com.blair.blairspring.model.ibatisschema");

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.current_session_context_class", "jta");

        /*jpaProperties.put("hibernate.transaction.manager_lookup_class",
                "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup");*/

        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.dialect.storage_engine", "innodb");

        factoryBean.setJpaProperties(jpaProperties);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(env.getProperty("spring.jpa.database-platform"));
        vendorAdapter.setShowSql(true);
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    /*public LocalContainerEntityManagerFactoryBean ibatisSchemaEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.blair.blairspring.model.ibatisschema");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
        em.setJpaPropertyMap(properties);
        return em;
    }*/

    /*public PlatformTransactionManager ibatisSchemaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(ibatisSchemaEntityManager().getObject());
        transactionManager.setValidateExistingTransaction(true);
        return transactionManager;
    }*/


    @Profile("jdbc")
    @Bean
    public JdbcTemplate jdbcTemplate() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(atomikosDataSourceBean);
        return jdbcTemplate;
    }

    @Profile("jdbc")
    @Bean
    public SimpleJdbcInsert simpleJdbcInsert() throws SQLException {
        return new SimpleJdbcInsert(atomikosDataSourceBean);
    }

}
