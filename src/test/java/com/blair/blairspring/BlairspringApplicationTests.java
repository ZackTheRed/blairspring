package com.blair.blairspring;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.blair.blairspring.model.ibatisschema.Job;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
class BlairspringApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BlairspringApplicationTests.class);

    @Value("${local.management.port}")
    private int serverPort;

    @LocalServerPort
    int randomServerPort;

    private String managementUrl = "http://localhost";

    private static TestRestTemplate restTemplate;

    @BeforeAll
    static void init() {
        restTemplate = new TestRestTemplate("john", "doe");
    }

    @BeforeEach
    void setUp() {
        managementUrl = managementUrl + ":" + serverPort + "/actuator";
    }

    @Test
    void testGetJob() {
        String url = "http://localhost:" + randomServerPort + "/jobs/1";
        ResponseEntity<Job> responseEntity = restTemplate.getForEntity(url, Job.class);
        logger.info("Retrieved Job: " + responseEntity.getBody().getDescription());
        //ResponseEntity<Job> jobResponseEntity = restTemplate.getForObject(url, Job.class);
    }

    @Test
    void testPostJob() {
        HttpEntity<Job> request = new HttpEntity<>(new Job("Policeman"));
        URI location = restTemplate.postForLocation("http://localhost:" + randomServerPort + "/jobs", request);
        assertThat(location, notNullValue());
    }

    @Test
    void testCreateReadUpdateDelete() {
        String url = "http://localhost:" + randomServerPort + "/jobs";

        HttpEntity<Job> request = new HttpEntity<>(new Job("Policeman2"));

        ResponseEntity<Job> response = restTemplate.exchange(url, HttpMethod.POST, request, Job.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
        assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));

        Job job = response.getBody();
        request = new HttpEntity<>(job);
       // response = restTemplate.exchange(url + "/" + job.getId(), HttpMethod.PUT, )


    }

    @Test
    void shouldReturn200WhenSendingRequestToHealthEndpoint() {
        ResponseEntity<Map> entity = restTemplate.getForEntity(managementUrl + "/health", Map.class);

        //Map<String, String> content = (Map<String, String>) entity.getBody().get("status");

        assertAll(
                () -> assertEquals(HttpStatus.OK, entity.getStatusCode())
        );
    }

}
