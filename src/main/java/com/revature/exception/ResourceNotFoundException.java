package com.revature.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException{

    public ResourceNotFoundException(Class<?> clazz, String property, Object value) {
        super(
                HttpStatus.NOT_FOUND,
                String.format("Could not find %s with %s: %s", clazz.getSimpleName().toLowerCase(), property, value.toString())
        );
    }
}