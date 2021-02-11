package com.blair.blairspring.util;

import com.blair.blairspring.util.genericsbeans.GenericsBean;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Getter
@Slf4j
@ToString
public class SetterInjectedBean {

    private GenericsBean<Integer> myGenericsBeans;
    private ITestBean testBean;
    private LazyBean lazyBean;
    private RandomClass randomClass;

    @PostConstruct
    private void postConstruct() {
        log.info("setterInjectedBean: {}", this);
    }

    //@Autowired
    public void setMyGenericsBeans(GenericsBean<Integer> myGenericsBeans) {
        this.myGenericsBeans = myGenericsBeans;
    }

    //@Autowired
    public void setTestBean(ITestBean testBean) {
        this.testBean = testBean;
    }

    //@Autowired
    public void katiLazyBean(LazyBean lazyBean) {
        this.lazyBean = lazyBean;
    }

    //@Autowired(required = false)
    public void setRandomClass(RandomClass randomClass) {
        this.randomClass = randomClass;
    }
}
