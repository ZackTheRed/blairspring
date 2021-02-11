package com.blair.blairspring;

import com.blair.blairspring.repositories.ibatisschema.jpa.JobRepository;
import com.blair.blairspring.services.implementations.jpa.EmployeeServiceImpl;
import com.blair.blairspring.util.Lilimpakis;
import com.blair.blairspring.util.RandomClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(properties = {"property1=property1", "property2=property2"})
@ActiveProfiles({"jpa", "test"})
@Slf4j
public class SpringTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ServletContext servletContext;

    @Value("${property1}")
    private String property1;

    @Autowired
    private Environment environment;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RandomClass randomClass;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private Lilimpakis lilimpakis;

    @BeforeEach
    public void beforeEach() {
        assertAll(
                () -> assertNotNull(employeeService),
                () -> assertNotNull(jobRepository)
        );
    }

    @Test
    public void nothing() {
        assertEquals(property1, "property1");
    }

    @Test
    @Order(3)
    @Transactional
    void testJobRepositoryUpdate() {
        assertEquals(jobRepository.updateJobDescription("Singer", "Singer2"), 0);
    }

    @Test
    void testJobRepositoryFind() {
        String firstJob = jobRepository.findAll().get(0).getDescription();
        assertAll(
                () -> assertTrue(jobRepository.findJobByDescriptionIndexed(firstJob) != null),
                () -> assertTrue(jobRepository.findJobByDescriptionParam(firstJob) != null)
        );
        log.info("All jobs sorted by description: {}", jobRepository.findAllJobs(Sort.by("description").descending()));
        log.info("Jobs paged: {}", jobRepository.findAllJobsPaged(PageRequest.of(0, 2)).getContent());
        log.info("Jobs in: {}", jobRepository.findAllJobsIn(List.of("Trainer", "Sales Executive")));
    }

    @Test
    void testRandomClass() {
        log.info("random number: {}", randomClass.getRandomDouble());
    }

    @Test
    void testEntityManager() {
        log.info("entityManager: {}", entityManager);
        assertNotNull(entityManager);
    }

    @Test
    void testTransactionManager() {
        log.info("transactionManager: {}", transactionManager);
        assertNotNull(transactionManager);
    }

    @Test
    void lilimpakisTest() {
        log.info("lilimpakis: {}", lilimpakis);
        assertEquals(lilimpakis.getYearBorn(), "1984");
        assertTrue(applicationContext.containsBean("lazyBean"));
        assertTrue(applicationContext.containsBeanDefinition("lazyBean"));
    }

    @Test
    void environmentTest() {
        assertNotNull(environment);
        ((AbstractEnvironment) environment).getPropertySources().forEach(propertySource -> log.info("propertySource: {}", propertySource));
        PropertiesPropertySource propertiesPropertySource = (PropertiesPropertySource) ((AbstractEnvironment) environment)
                .getPropertySources()
                .stream()
                .filter(propertySource -> propertySource.getName().equals("systemProperties"))
                .findFirst()
                .get();

        propertiesPropertySource.getSource().entrySet().stream().forEach(entry -> log.info("entry: {}", entry));
        System.getProperties().forEach((k, v) -> log.info("k: {}, v: {}", k, v));
    }

    @Test
    void testServletContext() {
        assertNotNull(servletContext);
    }
}
