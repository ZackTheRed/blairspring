package com.blair.blairspring.events;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class CustomEvent extends ApplicationEvent {

    private final String message;

    public CustomEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

}
