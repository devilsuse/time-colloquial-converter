package com.andela.colloquial.converter.exception;

/**
 * Thrown when user input is invalid.
 */
public class InvalidInputException extends ApplicationException {
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}


