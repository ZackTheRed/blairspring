package com.blair.blairspring.util;

import com.blair.blairspring.util.genericsbeans.GenericsBean;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@Getter
@ToString
public class TestComponent implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private GenericsBean<Integer> myGenericsBeans;
    private ITestBean testBean;
    private LazyBean lazyBean;
    private RandomClass randomClass;

    @Value("#{prototypeBean.myNumber}")
    private int number;

    @Autowired(required = false)
    public TestComponent(GenericsBean<Integer> myGenericsBeans,
                         /*@Qualifier("prototypeTestBean") */ITestBean testBean,
                         LazyBean lazyBean,
                         RandomClass randomClass) {
        this.myGenericsBeans = myGenericsBeans;
        this.testBean = testBean;
        this.lazyBean = lazyBean;
        this.randomClass = randomClass;
    }

    @Autowired(required = false)
    public TestComponent(GenericsBean<Integer> myGenericsBeans, ITestBean testBean, RandomClass randomClass) {
        this.myGenericsBeans = myGenericsBeans;
        this.testBean = testBean;
        this.randomClass = randomClass;
    }

    // @PostConstruct
    /*private void myPostConstructMethod() {
        log.info("Generics bean type: {}", myGenericsBeans.returnType());
    }*/

    @PostConstruct
    private void myPostConstructMethod() {
        log.info("TestComponent toString: {}", this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

/*@Bean
    private RandomClass randomClass() {
        return new RandomClass();
    }*/

}
