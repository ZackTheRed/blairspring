package com.blair.blairspring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BeanInstantiationMonitor {

    private static final Logger logger = LoggerFactory.getLogger(BeanInstantiationMonitor.class);

    @Before("beanAnnotatedMethods() && withinBlairPackage()")
    public void beforeFindById(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method " + className + "." + methodName + " is about to be called");
    }

    @Pointcut("@annotation(org.springframework.context.annotation.Bean)")
    public void beanAnnotatedMethods() {}

    @Pointcut("within(com.blair.blairspring..*)")
    public void withinBlairPackage() {}

}
