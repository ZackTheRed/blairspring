package com.blair.blairspring.aop.parent;

import org.springframework.stereotype.Component;

@Component
public class Person implements Human {

    @Override
    public void breath() {
        System.out.println("Hey, I can breath");
    }
}
