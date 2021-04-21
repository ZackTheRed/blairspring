package com.blair.blairspring.aop;

import com.blair.blairspring.events.CustomEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Aspect
@Component
@Slf4j
public class TestControllerAdvisor implements Ordered {

    @Autowired
    private CustomEventPublisher eventPublisher;

    private static final int DEFAULT_MAX_RETRIES = 5;

    private int maxRetries = DEFAULT_MAX_RETRIES;
    private int order = 1;

    @Around("inTestController() && homeMethod()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        int numberOfAttempts = 0;
        RuntimeException runtimeException;
        do {
            numberOfAttempts++;
            try {
                log.debug("Attempt #{}", numberOfAttempts);
                return proceedingJoinPoint.proceed();
            } catch (RuntimeException e) {
                runtimeException = e;
            }
        } while (numberOfAttempts <= maxRetries);
        throw runtimeException;
    }

    @AfterReturning(pointcut = "inTestController()", returning = "returnedObject")
    public void afterReturning(Object returnedObject) {
        log.debug("Returned object: {}", returnedObject);
    }

    @After("inTestController() && homeMethod()")
    public void after(JoinPoint joinPoint) {
        log.debug("joinPoint.getSignature: {}", joinPoint.getSignature());
        log.debug("joinPoint.getArgs: {}", joinPoint.getArgs());
        log.debug("joinPoint.getKind: {}", joinPoint.getKind());
        eventPublisher.publishCustomEvent("Blair custom event");
    }

    @AfterThrowing(value = "inTestController()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.debug("Exception thrown: {}", ex.getMessage());
    }

    @Around(value = "inTestController() && testRolesAllowedMethod()")
    public Object aroundTestRolesAllowedMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature codeSignature = (CodeSignature) proceedingJoinPoint.getSignature();
        String[] parameters = codeSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();
        IntStream.range(0, parameters.length).forEach(i -> log.info(parameters[i] + ": {}", args[i]));
        return proceedingJoinPoint.proceed();
    }

    @Pointcut("target(com.blair.blairspring.controllers.TestController)")
    private void inTestController() {
    }

    @Pointcut("execution(* com.blair.blairspring.controllers.TestController.home(..))")
    private void homeMethod() {
    }

    @Pointcut("execution(* com.blair.blairspring.controllers.TestController.testRolesAllowed(..))")
    private void testRolesAllowedMethod() {
    }

    @Override
    public int getOrder() {
        return order;
    }
}
