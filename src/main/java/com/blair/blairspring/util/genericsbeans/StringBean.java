package com.blair.blairspring.util.genericsbeans;

import org.springframework.stereotype.Component;

@Component
public class StringBean implements GenericsBean<String> {

    @Override
    public Class<String> returnType() {
        return String.class;
    }
}
