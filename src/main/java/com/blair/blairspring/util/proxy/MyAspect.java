package com.blair.blairspring.util.proxy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspect {

    @Around("withinProxyPackage()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("===============================");
        log.info("Executing around advice");
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.info("{} caught", e.toString());
        }
    }

    @AfterReturning(pointcut = "withinProxyPackage()", returning = "returnedValue")
    public void afterReturning(JoinPoint joinPoint, Object returnedValue) {
        log.info("Executing afterReturning advice");
        log.info("{} returned: {}", joinPoint.getSignature().getName(), returnedValue);
    }

    @AfterThrowing(pointcut = "withinProxyPackage()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.info("Executing afterThrowing advice");
        log.info("{} has thrown exception: {}", joinPoint.getSignature().getName(), ex.toString());
    }

    @After("withinProxyPackage()")
    public void after(JoinPoint joinPoint) {
        log.info("Executing after advice");
        log.info("{} returned", joinPoint.getSignature().getName());
    }

    @After("withinProxyPackage() && annotatedWithAOPed()")
    public void afterWithinPackageAndAnnotated(JoinPoint joinPoint) {
        log.info("Executing afterWithinPackageAndAnnotated");
        log.info("{} returned", joinPoint.getSignature().getName());
    }
    @Pointcut("within(com.blair.blairspring.util.proxy..*)")
    private void withinProxyPackage() {
    }

    @Pointcut("@within(com.blair.blairspring.annotations.AOPed)")
    private void annotatedWithAOPed() {

    }
}
