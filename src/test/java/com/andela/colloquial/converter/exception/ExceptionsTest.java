package com.andela.colloquial.converter.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionsTest {

    @Test
    @DisplayName("ApplicationException stores message and cause")
    void applicationException_messageAndCause() {
        Throwable cause = new IllegalArgumentException("bad");
        ApplicationException ex1 = new ApplicationException("msg");
        ApplicationException ex2 = new ApplicationException("msg2", cause);

        assertEquals("msg", ex1.getMessage());
        assertEquals("msg2", ex2.getMessage());
        assertSame(cause, ex2.getCause());
    }

    @Test
    @DisplayName("InvalidInputException stores message and cause")
    void invalidInputException_messageAndCause() {
        Throwable cause = new NumberFormatException("nfe");
        InvalidInputException ex1 = new InvalidInputException("invalid");
        InvalidInputException ex2 = new InvalidInputException("invalid2", cause);

        assertEquals("invalid", ex1.getMessage());
        assertEquals("invalid2", ex2.getMessage());
        assertSame(cause, ex2.getCause());
    }

    @Test
    @DisplayName("LocalizationException stores message and cause")
    void localizationException_messageAndCause() {
        Throwable cause = new RuntimeException("oops");
        LocalizationException ex1 = new LocalizationException("loc");
        LocalizationException ex2 = new LocalizationException("loc2", cause);

        assertEquals("loc", ex1.getMessage());
        assertEquals("loc2", ex2.getMessage());
        assertSame(cause, ex2.getCause());
    }
}


