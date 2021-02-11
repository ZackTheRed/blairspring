package com.blair.blairspring.util.proxy;

import com.blair.blairspring.annotations.AOPed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AOPed
@Component
@Slf4j
public class AOPedClass {

    public void methodOfAOPedClass() {
        log.info("Running methodOfAOPedClass");
    }

}
