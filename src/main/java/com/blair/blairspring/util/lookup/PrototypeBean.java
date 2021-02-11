package com.blair.blairspring.util.lookup;

import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class PrototypeBean {

    private int myNumber = new Random().nextInt();

    public PrototypeBean getInstance() {
        return this;
    }

}
