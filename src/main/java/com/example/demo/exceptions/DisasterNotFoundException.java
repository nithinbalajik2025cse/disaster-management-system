package com.example.demo.exceptions;

public class DisasterNotFoundException extends RuntimeException {
    public DisasterNotFoundException(String message) {
        super(message);
    }
}
