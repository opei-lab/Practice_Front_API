package com.example.practiceapi.exception;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(String message) {
        super(401, "Unauthorized", message);
    }
}
