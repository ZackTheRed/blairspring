package com.blair.blairspring.aop;

import com.blair.blairspring.aop.parent.Human;
import com.blair.blairspring.aop.parent.Person;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*@Aspect
@Component*/
public class DeclareParentsAspect {

    @DeclareParents(value="com.blair.blairspring.aop.subclasses.Player+", defaultImpl = Person.class)
    public static Human alsoHuman;

    @Before("isPlayer() && this(human)")
    public void showThatYouCanAlsoBreath(JoinPoint joinPoint, Human human) {
        Object targetObject  = joinPoint.getTarget();
        ((Human) targetObject).breath();
    }

    @Pointcut("execution(* com.blair.blairspring.aop.subclasses.Player+.*(..))")
    private void isPlayer() {

    }

}
