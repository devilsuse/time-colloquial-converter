package com.andela.colloquial.converter.exception;

/**
 * Thrown when localization resources are missing or inconsistent.
 */
public class LocalizationException extends ApplicationException {
    public LocalizationException(String message) {
        super(message);
    }

    public LocalizationException(String message, Throwable cause) {
        super(message, cause);
    }
}


