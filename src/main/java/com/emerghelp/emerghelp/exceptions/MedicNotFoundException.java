package com.emerghelp.emerghelp.exceptions;

public class MedicNotFoundException extends RuntimeException {
    public MedicNotFoundException(String message) {
        super(message);
    }
}
