package com.blair.blairspring.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Random;

@Getter
@Component
public class RandomClass {

    private final double randomDouble;

    public RandomClass() {
        randomDouble = new Random().nextDouble();
    }

}
