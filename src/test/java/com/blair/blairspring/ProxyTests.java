package com.blair.blairspring;

import com.blair.blairspring.util.proxy.AOPedClass;
import com.blair.blairspring.util.proxy.CGLIBedProxy;
import com.blair.blairspring.util.proxy.IProxied;
import com.blair.blairspring.util.proxy.ProxiedAOPConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProxiedAOPConfiguration.class)
@ActiveProfiles("proxy-test")
@Slf4j
public class ProxyTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IProxied proxied;

    @Autowired
    private CGLIBedProxy cglibedProxy;

    @Autowired
    private AOPedClass aopClass;

    @BeforeEach
    void beforeEach() {
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(beanName -> log.info(beanName));
        assertNotNull(proxied);
    }

    @Test
    void testPublicMethod() {
        proxied.publicMethod();
    }

    @Test
    void testFinalMethod() {
        proxied.finalMethod();
    }

    @Test
    void testThrowingExceptionMethod() {
        proxied.throwingExceptionMethod();
    }

    @Test
    void testCGLIBedProxy() {
        cglibedProxy.publicMethod();
        cglibedProxy.finalMethod();
    }

    @Test
    void testStringReturningMethod() {
        proxied.stringReturningMethod();
    }

    @Test
    void testAnnotated() {
        cglibedProxy.publicMethod();
        aopClass.methodOfAOPedClass();
    }

}
