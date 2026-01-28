package com.example.practiceapi.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(404, "Not Found", message);
    }
}
