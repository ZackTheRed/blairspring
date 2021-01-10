package com.blair.blairspring.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestComponent implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final TestBean myTestBean;

    public TestComponent(@Lazy @Qualifier("testBean2") TestBean someTestBean) {
        this.myTestBean = someTestBean;
    }

    @PostConstruct
    private void myPostConstructMethod() {
        myTestBean.testMethod();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
