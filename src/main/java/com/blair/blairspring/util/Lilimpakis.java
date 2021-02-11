package com.blair.blairspring.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("lilimpakis")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
@ToString
public class Lilimpakis {

    private final String firstName;
    private final String lastName;
    private final String favouriteColor;
    private final String yearBorn;

}
