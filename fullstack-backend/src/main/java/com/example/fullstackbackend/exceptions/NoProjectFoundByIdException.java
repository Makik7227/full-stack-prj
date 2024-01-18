package com.example.fullstackbackend.exceptions;

public class NoProjectFoundByIdException extends RuntimeException {
    public NoProjectFoundByIdException(String message) {
        super(message);
    }
}
