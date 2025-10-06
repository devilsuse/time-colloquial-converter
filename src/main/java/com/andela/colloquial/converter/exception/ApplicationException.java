package com.andela.colloquial.converter.exception;

/**
 * Base unchecked exception for the application.
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}


