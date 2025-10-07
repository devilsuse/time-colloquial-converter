package com.andela.colloquial.converter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.andela.colloquial.converter.exception.ApplicationException;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

class TimeToColloquialAppErrorHandlingIntegrationTest {

    static class ThrowingStrategy implements TimeToSpeechStrategy {
        @Override
        public String convertToSpokenForm(java.time.LocalTime time) {
            throw new ApplicationException("boom");
        }
    }

    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        originalOut = System.out;
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    @SuppressWarnings("unused")
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("process handles ApplicationException via ErrorHandler and does not print success marker")
    void process_handles_error_and_no_success_output() throws Exception {
        TimeToSpeechStrategy throwing = new ThrowingStrategy();

        Method process = TimeToColloquialApp.class.getDeclaredMethod("process", com.andela.colloquial.converter.strategy.TimeToSpeechStrategy.class, String.class);
        process.setAccessible(true);
        process.invoke(null, throwing, "2:05");

        String out = capturedOut.toString(StandardCharsets.UTF_8);
        assertFalse(out.contains("===>"));
        // Ensure the error was logged by ErrorHandler
        assertTrue(out.contains("Application error:"));
    }
}


