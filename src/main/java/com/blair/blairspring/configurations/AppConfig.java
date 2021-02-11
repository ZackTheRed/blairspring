package com.blair.blairspring.configurations;

import com.blair.blairspring.util.ITestBean;
import com.blair.blairspring.util.LazyBean;
import com.blair.blairspring.util.SetterInjectedBean;
import com.blair.blairspring.util.genericsbeans.GenericsBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    /*@Bean
    public ServletRegistrationBean restServlet() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ChildCtxConfig.class); ...
        dispatcherServlet.setApplicationContext(applicationContext);
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/child/*"); ...
        return servletRegistrationBean;
    }*/

}
