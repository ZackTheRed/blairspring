package com.blair.blairspring.configurations;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import com.blair.blairspring.controllers.EmployeeController;
import com.blair.blairspring.controllers.NonAnnotatedController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(value = EmployeeController.class)
@Slf4j
public class ConditionalConfiguration {

    @Bean
    public ServletRegistrationBean<ViewStatusMessagesServlet> viewStatusMessagesServlet() {
        ServletRegistrationBean<ViewStatusMessagesServlet> bean = new ServletRegistrationBean<ViewStatusMessagesServlet>(new ViewStatusMessagesServlet(), "/lbClassicStatus");
        bean.setLoadOnStartup(1);
        log.info("Registered ViewStatusMessagesServlet");
        return bean;
    }

    @Bean("/nonAnnotatedController")
    public NonAnnotatedController nonAnnotatedController() {
        NonAnnotatedController nonAnnotatedController = new NonAnnotatedController();
        nonAnnotatedController.setSupportedMethods("GET");
        return nonAnnotatedController;
    }

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return factory -> {
            var registry = (BeanDefinitionRegistry) factory;
            registry.registerBeanDefinition("myBean", BeanDefinitionBuilder.genericBeanDefinition(String.class)
                    .addConstructorArgValue("This is myBean")
                    .getBeanDefinition()
            );
            BeanDefinition primaryTestBeanDefinition = factory.getBeanDefinition("primaryTestBean");
            primaryTestBeanDefinition.setPrimary(false);
            BeanDefinition thirdTestBeanDefinition = factory.getBeanDefinition("thirdTestBean");
            thirdTestBeanDefinition.setPrimary(true);
        };
    }

}
