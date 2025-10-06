package com.andela.colloquial.converter.strategy;

import java.time.LocalTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BritishTimeToSpeechStrategyTest {

    private final TimeToSpeechStrategy strategy = new BritishTimeToSpeechStrategy(Locale.UK);

    @Test
    @DisplayName("Specials: midnight and noon")
    void specials_midnight_noon() {
        assertEquals("midnight", strategy.convertToSpokenForm(LocalTime.of(0, 0)));
        assertEquals("noon", strategy.convertToSpokenForm(LocalTime.of(12, 0)));
    }

    @Test
    @DisplayName("Exact hours")
    void exact_hours() {
        assertEquals("one o'clock", strategy.convertToSpokenForm(LocalTime.of(1, 0)));
        assertEquals("ten o'clock", strategy.convertToSpokenForm(LocalTime.of(10, 0)));
    }

    @Test
    @DisplayName("Past/half/quarter")
    void past_half_quarter() {
        assertEquals("five past two", strategy.convertToSpokenForm(LocalTime.of(2, 5)));
        assertEquals("quarter past three", strategy.convertToSpokenForm(LocalTime.of(3, 15)));
        assertEquals("half past four", strategy.convertToSpokenForm(LocalTime.of(4, 30)));
    }

    @Test
    @DisplayName("Early thirties and to next hour")
    void early_thirties_and_to_next_hour() {
        assertEquals("five thirty one", strategy.convertToSpokenForm(LocalTime.of(5, 31)));
        assertEquals("one to ten", strategy.convertToSpokenForm(LocalTime.of(9, 59)));
        assertEquals("quarter to eight", strategy.convertToSpokenForm(LocalTime.of(7, 45)));
    }
}


