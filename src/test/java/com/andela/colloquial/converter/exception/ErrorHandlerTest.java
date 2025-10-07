package com.andela.colloquial.converter.exception;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ErrorHandlerTest {

    @Test
    @DisplayName("handleAndReport: handles InvalidInputException via warn path")
    void handlesInvalidInput() {
        assertDoesNotThrow(() -> ErrorHandler.handleAndReport(new InvalidInputException("bad input")));
    }

    @Test
    @DisplayName("handleAndReport: handles LocalizationException via error path")
    void handlesLocalization() {
        assertDoesNotThrow(() -> ErrorHandler.handleAndReport(new LocalizationException("loc issue")));
    }

    @Test
    @DisplayName("handleAndReport: handles ApplicationException via error path")
    void handlesApplication() {
        assertDoesNotThrow(() -> ErrorHandler.handleAndReport(new ApplicationException("app issue")));
    }

    @Test
    @DisplayName("handleAndReport: handles arbitrary Throwable via fallback error path")
    void handlesOtherThrowable() {
        assertDoesNotThrow(() -> ErrorHandler.handleAndReport(new RuntimeException("boom")));
    }
}


