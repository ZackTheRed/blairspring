package com.blair.blairspring.exceptions;

public class NotFoundException extends RuntimeException {

    public <T> NotFoundException(Class<T> clazz, Long id) {
        super(clazz.getSimpleName() + " with id: " + id + " does not exist!");
    }

    public <T> NotFoundException(Class<T> clazz, Object key) {
        super(clazz.getSimpleName() + " with key: " + key + " does not exist!");
    }

}
