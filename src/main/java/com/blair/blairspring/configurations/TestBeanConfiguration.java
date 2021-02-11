package com.blair.blairspring.configurations;

import com.blair.blairspring.util.ITestBean;
import com.blair.blairspring.util.TestBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class TestBeanConfiguration {

    @Bean(initMethod = "myInit", destroyMethod = "myDestroy")
    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public TestBean primaryTestBean() {
        return new TestBean("primaryTestBean");
    }

    @Bean
    @Lazy
    @Order(2)
    public TestBean lazyTestBean() {
        return new TestBean("lazyTestBean");
    }

    @Bean
    @Order(3)
    public TestBean thirdTestBean() {
        return new TestBean("thirdTestBean");
    }

    @Bean
    @SessionScope
    @Order(4)
    public TestBean sessionTestBean() {
        return new TestBean("sessionTestBean");
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public TestBean prototypeTestBean() {
        return new TestBean("prototypeTestBean");
    }

}
