package com.blair.blairspring.configurations;

import ch.qos.logback.classic.ViewStatusMessagesServlet;
import com.blair.blairspring.controllers.EmployeeController;
import com.blair.blairspring.controllers.NonAnnotatedController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @ConditionalOnMissingClass("com.blair.blairspring.controllers.EmployeeController")
// @ConditionalOnClass(name = "com.blair.blairspring.controllers.EmployeeController")
// @ConditionalOnClass(value = EmployeeController.class)
@ConditionalOnBean(value = EmployeeController.class)
@Slf4j
public class ConditionalConfiguration {

    private final String myString;

    public ConditionalConfiguration(@Value("Dog") String myString) { // This is to demonstrate that no no-args constructor is necessary
        this.myString = myString;
    }

    @Bean
    public ServletRegistrationBean viewStatusMessagesServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new ViewStatusMessagesServlet(), "/lbClassicStatus");
        bean.setLoadOnStartup(1);
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
