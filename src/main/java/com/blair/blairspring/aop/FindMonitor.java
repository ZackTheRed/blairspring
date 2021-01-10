package com.blair.blairspring.aop;

import com.blair.blairspring.model.ibatisschema.Job;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FindMonitor {

    private static final Logger logger = LoggerFactory.getLogger(FindMonitor.class);

    private int jobTimesRun;
    private int employeeTimesRun;

    @Before("serviceFindById(id)")
    public void beforeFindById(JoinPoint joinPoint, Long id) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("ID: " + id);
        logger.info("Method " + className + "." + methodName + " is about to be called");
    }

    @AfterReturning(value = "serviceFindById(id)", returning = "returnedObject")
    public void afterReturningFindById(Long id, Object returnedObject) {
        if (returnedObject instanceof Job) {
            logger.info("Job Times run: " + ++jobTimesRun);
            logger.info("Job: " + returnedObject);
        } else {
            logger.info("Employee Times run: " + ++employeeTimesRun);
            logger.info("Employee: " + returnedObject);
        }
    }

    @Pointcut("execution(public * com.blair.blairspring.services.*Service.findById(..)) && args(id)")
    private void serviceFindById(Long id) {
    }

}
