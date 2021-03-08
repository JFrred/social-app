package com.example.exception;

public class SpringSocialAppException extends RuntimeException{
    public SpringSocialAppException(String message) {
        super(message);
    }

    public SpringSocialAppException(String message, Exception exception) {
        super(message, exception);
    }
}
