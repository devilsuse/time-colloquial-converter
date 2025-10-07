package com.andela.colloquial.converter.strategy;

import java.time.LocalTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AbstractTimeToSpeechStrategyMinuteNamesTest {

    private final TimeToSpeechStrategy strategy = new BritishTimeToSpeechStrategy(Locale.UK);

    @Test
    @DisplayName("Minutes 1–29 map to correct words: '[minute] past seven' with quarter special")
    void minuteNames_1_to_29_before_half_past() {
        String[] words = new String[]{
            "", // 0 unused
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "quarter", "sixteen", "seventeen", "eighteen", "nineteen",
            "twenty", "twenty one", "twenty two", "twenty three", "twenty four", "twenty five", "twenty six", "twenty seven", "twenty eight", "twenty nine"
        };

        for (int minute = 1; minute <= 29; minute++) {
            String spoken = strategy.convertToSpokenForm(LocalTime.of(7, minute));
            if (minute == 15) {
                assertEquals("quarter past seven", spoken);
            } else {
                assertEquals(words[minute] + " past seven", spoken);
            }
        }
    }
}


