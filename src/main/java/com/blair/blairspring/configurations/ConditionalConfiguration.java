package com.blair.blairspring.configurations;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import com.blair.blairspring.util.TestBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
// @ConditionalOnMissingClass("com.blair.blairspring.controllers.EmployeeController")
@ConditionalOnClass(name = "com.blair.blairspring.controllers.EmployeeController")
public class ConditionalConfiguration {

    @Bean
    public ServletRegistrationBean viewStatusMessagesServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new ViewStatusMessagesServlet(), "/lbClassicStatus");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean(initMethod = "myInit", destroyMethod = "myDestroy")
    //@RequestScope
    @Lazy
    @Primary
    public TestBean testBean() {
        return new TestBean();
    }

    @Bean
    @Lazy
    public TestBean testBean2() {
        return new TestBean();
    }

}
