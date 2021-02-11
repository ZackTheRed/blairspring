package com.blair.blairspring.configurations;

import com.blair.blairspring.interceptors.TestInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import javax.servlet.ServletContext;
import java.util.List;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    private final ServletContext servletContext;

    private final ObjectMapper objectMapper;

    public MvcConfiguration(ServletContext servletContext, ObjectMapper objectMapper) {
        this.servletContext = servletContext;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("view/home");
        registry.addViewController("/").setViewName("view/home");
        registry.addViewController("hello").setViewName("view/hello");
        registry.addViewController("/login").setViewName("view/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TestInterceptor()).addPathPatterns("/test/void");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(HAL_JSON, MediaType.APPLICATION_JSON));
        return converter;
    }

    /*@Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
    }*/

    /*@Bean(name = "excelViewResolver")
    public ViewResolver getXmlViewResolver() {
        XmlViewResolver resolver = new XmlViewResolver();
        resolver.setLocation(new ServletContextResource(servletContext, "/WEB-INF"));
    }*/
}
