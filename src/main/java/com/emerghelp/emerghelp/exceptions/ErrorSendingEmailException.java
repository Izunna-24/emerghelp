package com.emerghelp.emerghelp.exceptions;

public class ErrorSendingEmailException extends RuntimeException {
    public ErrorSendingEmailException(String message) {
        super(message);
    }
}
