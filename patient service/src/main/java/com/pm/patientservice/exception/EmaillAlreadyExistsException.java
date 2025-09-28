package com.pm.patientservice.exception;

public class EmaillAlreadyExistsException extends RuntimeException {
    public EmaillAlreadyExistsException(String message) {
        super(message);
    }
}
