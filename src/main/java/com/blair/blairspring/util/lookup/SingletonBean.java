package com.blair.blairspring.util.lookup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SingletonBean {

    public PrototypeBean usePrototype() {
        PrototypeBean prototypeBean = getPrototypeBean();
        log.info("Prototype bean number: {}", prototypeBean.getMyNumber());
        return prototypeBean.getInstance();
    }

    @Lookup
    public PrototypeBean getPrototypeBean() {
        return null;
    }

}
