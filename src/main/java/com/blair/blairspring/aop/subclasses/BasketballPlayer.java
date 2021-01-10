package com.blair.blairspring.aop.subclasses;

import org.springframework.stereotype.Component;

@Component
public class BasketballPlayer implements Player {

    @Override
    public void play() {
        System.out.println("Playing basketball");
    }
}
