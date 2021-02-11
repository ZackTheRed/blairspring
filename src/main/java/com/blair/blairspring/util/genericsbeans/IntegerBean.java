package com.blair.blairspring.util.genericsbeans;

import org.springframework.stereotype.Component;

@Component
public class IntegerBean implements GenericsBean<Integer> {

    @Override
    public Class<Integer> returnType() {
        return Integer.class;
    }

}
