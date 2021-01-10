package com.blair.blairspring.actuator;

import com.blair.blairspring.repositories.ibatisschema.EmployeeRepository;
import com.blair.blairspring.repositories.ibatisschema.JobRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@WebEndpoint(id = "dao")
public class DaoEndpoint implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ReadOperation
    public DaoHealth daoHealth(SecurityContext securityContext) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("user", securityContext.getPrincipal().getName());
        details.put("isUserInRoleAdmin", securityContext.isUserInRole("ADMIN"));
        if (jobCheck(applicationContext.getBean(JobRepository.class))) {
            details.put("jobInit", "SUCCESSFUL");
        }
        if (employeeCheck(applicationContext.getBean(EmployeeRepository.class))) {
            details.put("employeeInit", "SUCCESSFUL");
        }
        DaoHealth daoHealth = new DaoHealth();
        daoHealth.setDaoDetails(details);
        return daoHealth;
    }

    @ReadOperation
    public DaoHealth repositoryHealth(@Selector String name) {
        Map<String, Object> details = new LinkedHashMap<>();
        try {
            JpaRepository repository = (JpaRepository) applicationContext.getBean(name);

            if (repository instanceof JobRepository jobRepository && jobCheck(jobRepository)) {
                details.put("jobInit", "SUCCESSFUL");
            } else if (repository instanceof EmployeeRepository employeeRepository && employeeCheck(employeeRepository)) {
                details.put("employeeInit", "SUCCESSFUL");
            } else {
                details.put("repoInit", "N/A");
            }
        } catch (NoSuchBeanDefinitionException e) {
            details.put("repoInit", "NON-EXISTENT");
        }

        DaoHealth daoHealth = new DaoHealth();
        daoHealth.setDaoDetails(details);
        return daoHealth;
    }

    private boolean jobCheck(JobRepository jobRepository) {
        return jobRepository.findAll().size() > 0;
    }

    private boolean employeeCheck(EmployeeRepository employeeRepository) {
        return employeeRepository.findAll().size() > 0;
    }

}
