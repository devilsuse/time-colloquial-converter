package com.andela.colloquial.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
class TimeToColloquialAppIntegrationTest {

    private PrintStream originalOut;
    private java.io.InputStream originalIn;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUpStdStreams() {
        originalOut = System.out;
        originalIn = System.in;
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    @SuppressWarnings("unused")
    void restoreStdStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("main: converts valid input and exits on 'exit'")
    void main_convertsAndExits() throws Exception {
        String input = "2:05\nexit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        // run main
        TimeToColloquialApp.main(new String[0]);

        String out = capturedOut.toString(StandardCharsets.UTF_8);
        assertTrue(out.contains("Enter time:"));
        assertTrue(out.contains("===>five past two"));
    }

    @Test
    @DisplayName("main: handles invalid and blank inputs without crashing, continues prompting")
    void main_invalidAndBlankHandled() throws Exception {
        String input = "\n   \n2/05\nexit\n"; // blank, blank, invalid, then exit
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        TimeToColloquialApp.main(new String[0]);

        String out = capturedOut.toString(StandardCharsets.UTF_8);
        // should prompt multiple times and never print a conversion line for invalid input
        int promptCount = out.split("Enter time:").length - 1;
        assertTrue(promptCount >= 2);
        assertFalse(out.contains("===>"));
    }
}


