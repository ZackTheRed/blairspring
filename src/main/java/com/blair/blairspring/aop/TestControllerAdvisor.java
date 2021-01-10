package com.blair.blairspring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestControllerAdvisor implements Ordered {

    private static final int DEFAULT_MAX_RETRIES = 5;

    private int maxRetries = DEFAULT_MAX_RETRIES;
    private int order = 1;

    private static final Logger logger = LoggerFactory.getLogger(TestControllerAdvisor.class);

    @Around("inTestController()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int numberOfAttempts = 0;
        RuntimeException runtimeException;
        do {
            numberOfAttempts++;
            try {
                logger.debug("Attempt #{}", numberOfAttempts);
                return proceedingJoinPoint.proceed();
            } catch (RuntimeException e) {
                runtimeException = e;
            }
        } while(numberOfAttempts <= maxRetries);
        throw runtimeException;
    }

    @AfterReturning(pointcut = "inTestController()", returning = "returnedObject")
    public void afterReturning(Object returnedObject) {
        logger.debug("Returned object: {}", returnedObject);
    }

    @After("inTestController()")
    public void after(JoinPoint joinPoint) {
        logger.debug("joinPoint.getSignature: {}", joinPoint.getSignature());
        logger.debug("joinPoint.getArgs: {}", joinPoint.getArgs());
        logger.debug("joinPoint.getKind: {}", joinPoint.getKind());
    }

    @Pointcut("target(com.blair.blairspring.controllers.TestController)")
    private void inTestController() {
    }

    @Override
    public int getOrder() {
        return order;
    }
}
