package com.revature.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException{

    private static final String INVALID_CREDENTIALS = "Could not authenticate the provided credentials";

    public InvalidCredentialsException() {
        super(
                HttpStatus.UNAUTHORIZED,
                INVALID_CREDENTIALS);
    }
}