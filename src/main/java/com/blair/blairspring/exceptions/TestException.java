package com.blair.blairspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, reason = "Illegal bullshit")
public class TestException extends RuntimeException {

    public TestException(String message) {
        super(message);
    }
}
