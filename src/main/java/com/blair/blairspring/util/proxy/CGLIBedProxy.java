package com.blair.blairspring.util.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CGLIBedProxy {

    public void publicMethod() {
        log.info("Running publicMethod");
    }

    public final void finalMethod() {
        log.info("Running finalMethod");
    }

}
