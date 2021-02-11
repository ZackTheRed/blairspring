package com.blair.blairspring.actuator;

import com.blair.blairspring.services.JobService;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HealthChecker implements HealthIndicator, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Health health() {
        Map<String, String> inits = new HashMap<>();
        if (jobCheck(applicationContext.getBean(JobService.class))) {
            inits.put("jobInit", "SUCCESSFUL");
        } else {
            return Health.down().withDetail("jobInit", "FAILED").build();
        }

        return Health.up().withDetails(inits).build();
    }

    private boolean jobCheck(JobService jobService) {
        return jobService != null;
    }
}
