package com.andela.colloquial.converter.strategy;

import java.time.LocalTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AbstractTimeToSpeechStrategyTest {

    private final TimeToSpeechStrategy strategy = new BritishTimeToSpeechStrategy(Locale.UK);

    @Nested
    @DisplayName("Special cases")
    class SpecialCases {
        @Test
        void midnight() {
            assertEquals("midnight", strategy.convertToSpokenForm(LocalTime.of(0, 0)));
        }

        @Test
        void noon() {
            assertEquals("noon", strategy.convertToSpokenForm(LocalTime.of(12, 0)));
        }
    }

    @Test
    void exactHour_nonSpecial() {
        assertEquals("one o'clock", strategy.convertToSpokenForm(LocalTime.of(1, 0)));
        assertEquals("eleven o'clock", strategy.convertToSpokenForm(LocalTime.of(23, 0)));
    }

    @Nested
    @DisplayName("Standard five-minute multiples")
    class StandardMultiples {
        @Test
        void quarterPast() {
            assertEquals("quarter past three", strategy.convertToSpokenForm(LocalTime.of(3, 15)));
        }

        @Test
        void halfPast() {
            assertEquals("half past ten", strategy.convertToSpokenForm(LocalTime.of(10, 30)));
        }

        @Test
        void quarterTo() {
            assertEquals("quarter to six", strategy.convertToSpokenForm(LocalTime.of(5, 45)));
        }
    }

    @Nested
    @DisplayName("Before half past")
    class BeforeHalfPast {
        @Test
        void minute_1() {
            assertEquals("one past four", strategy.convertToSpokenForm(LocalTime.of(4, 1)));
        }

        @Test
        void minute_29() {
            assertEquals("twenty nine past seven", strategy.convertToSpokenForm(LocalTime.of(7, 29)));
        }
    }

    @Nested
    @DisplayName("Past half past")
    class PastHalfPast {
        @Test
        void minute_31_early_thirties() {
            assertEquals("two thirty one", strategy.convertToSpokenForm(LocalTime.of(2, 31)));
        }

        @Test
        void minute_59_to_next_hour() {
            assertEquals("one to ten", strategy.convertToSpokenForm(LocalTime.of(9, 59)));
        }
    }
}

