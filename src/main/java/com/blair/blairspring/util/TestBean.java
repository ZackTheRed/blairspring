package com.blair.blairspring.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
@ToString
@JsonSerialize(as = TestBean.class)
public class TestBean implements ITestBean,
        InitializingBean,
        DisposableBean,
        BeanNameAware,
        BeanFactoryAware,
        ApplicationContextAware {

    private final String id;
    private final String testBeanProperty;
    private final List<String> strings;
    private String beanName;
    private static final AtomicInteger counter = new AtomicInteger(0);

    public TestBean(String testBeanProperty) {
        counter.incrementAndGet();
        this.id = "TestBean_ver_" + new Random().nextLong();
        this.testBeanProperty = testBeanProperty;
        strings = new ArrayList<>();
    }

    @Override
    public void setBeanName(String s) {
        log.info("=====================================");
        log.info("Running setBeanName method of {}", s);
        this.beanName = s;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("Running setBeanFactory method of {}", beanName);
        log.info("{} is singleton: {}", beanName, beanFactory.isSingleton(beanName));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("Running setApplicationContext method of {}", beanName);
    }

    @PostConstruct
    private void postConstruct() {
        log.info("Running postConstruct method of {}", beanName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Running afterPropertiesSet method of {}", beanName);
        log.info("testBeanProperty of {}: {}", beanName, testBeanProperty);
    }

    @Override
    public void myInit() {
        log.info("Running myInit method of {}", beanName);
        log.info("{} toString: {}", beanName, this);
        log.info("=====================================");
    }

    @PreDestroy
    private void preDestroy() {
        log.info("=====================================");
        log.info("Running preDestroy method of {}", beanName);
    }

    @Override
    public void destroy() {
        log.info("Running destroy method of {}", beanName);
    }

    @Override
    public void myDestroy() {
        log.info("Running myDestroy method of {}", beanName);
        log.info("=====================================");
    }

    public void testMethod() {
        log.info("Running testMethod method of {}", testBeanProperty);
        log.info("Counter: {}", counter);
    }

}
