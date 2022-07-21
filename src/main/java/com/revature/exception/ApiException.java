package com.revature.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class ApiException extends RuntimeException{
    private Integer status;
    private String error;
    private String path;
    private String message;

    public ApiException(HttpStatus status, String message){
        super(message);
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public ApiException setStatus(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public String getError() {
        return error;
    }

    public ApiException setError(String error) {
        this.error = error;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ApiException setPath(String path) {
        this.path = path;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiException setMessage(String message) {
        this.message = message;
        return this;
    }
}