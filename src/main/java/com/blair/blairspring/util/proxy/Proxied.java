package com.blair.blairspring.util.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Proxied implements IProxied {

    public void publicMethod() {
       log.info("Running publicMethod");
    }

    protected void protectedMethod() {
        log.info("Running protectedMethod");
    }

    public final void finalMethod() {
        log.info("Running finalMethod");
    }

    public void throwingExceptionMethod() {
        throw new RuntimeException("RuntimeException message");
    }

    @Override
    public String stringReturningMethod() {
        return "Some String";
    }
}
