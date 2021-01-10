package com.blair.blairspring.controllers;

import com.blair.blairspring.util.TestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private ApplicationContext applicationContext;

    private int attempts = 0;

    public TestController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping("home")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> home() {
        attempts++;
        TestBean testBean = (TestBean) applicationContext.getBean("testBean");
        logger.info("TestBean object: {}", testBean);
        testBean.testMethod();

        logger.info("Count of testBean beans: {}", Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(beanName -> beanName.equals("testBean"))
                .count());

        if (attempts < 5) throw new RuntimeException();

        return ResponseEntity.ok().body("home test");
    }

}
