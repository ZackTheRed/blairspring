package com.blair.blairspring.configurations;

import com.blair.blairspring.util.ITestBean;
import com.blair.blairspring.util.LazyBean;
import com.blair.blairspring.util.SetterInjectedBean;
import com.blair.blairspring.util.genericsbeans.GenericsBean;
import org.springframework.context.annotation.Bean;

public class BeanDefinitionsConfiguration {

    @Bean
    public SetterInjectedBean setterInjectedBean(GenericsBean<Integer> myGenericsBeans, ITestBean testBean, LazyBean lazyBean) {
        SetterInjectedBean setterInjectedBean = new SetterInjectedBean();
        setterInjectedBean.setMyGenericsBeans(myGenericsBeans);
        setterInjectedBean.setTestBean(testBean);
        setterInjectedBean.katiLazyBean(lazyBean);
        return setterInjectedBean;
    }

}
