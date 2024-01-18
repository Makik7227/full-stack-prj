package com.example.fullstackbackend.exceptions;

public class NoEmployeeFoundByIdException extends RuntimeException {
    public NoEmployeeFoundByIdException(String message) {
        super(message);
    }
}
