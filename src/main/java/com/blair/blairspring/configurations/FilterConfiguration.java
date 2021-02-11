package com.blair.blairspring.configurations;

import com.blair.blairspring.filters.TestFilter;
import com.blair.blairspring.util.TestComponent;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    private final TestComponent testComponent;

    public FilterConfiguration(TestComponent testComponent) {
        this.testComponent = testComponent;
    }

    @Bean
    public FilterRegistrationBean<TestFilter> testFilter() {
        FilterRegistrationBean<TestFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TestFilter(testComponent));
        registrationBean.addUrlPatterns("/test/void");
        return registrationBean;
    }
}
