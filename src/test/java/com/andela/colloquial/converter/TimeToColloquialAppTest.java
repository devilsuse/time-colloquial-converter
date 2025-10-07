package com.andela.colloquial.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.andela.colloquial.converter.exception.InvalidInputException;
import com.andela.colloquial.converter.strategy.BritishTimeToSpeechStrategy;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

class TimeToColloquialAppTest {

    private final TimeToSpeechStrategy strategy = new BritishTimeToSpeechStrategy(Locale.UK);

    private String invokeConvert(TimeToSpeechStrategy s, String input) {
        try {
            Method m = TimeToColloquialApp.class.getDeclaredMethod("convert", TimeToSpeechStrategy.class, String.class);
            m.setAccessible(true);
            return (String) m.invoke(null, s, input);
        } catch (InvocationTargetException e) {
            // unwrap and rethrow if it's a runtime exception we expect in tests
            if (e.getTargetException() instanceof RuntimeException re) {
                throw re;
            }
            throw new RuntimeException(e.getTargetException());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    @SuppressWarnings("unused")
    @DisplayName("convert: negative inputs")
    class ConvertNegative {
        @Test
        void nullInput_throwsInvalidInputException() {
            InvalidInputException ex = assertThrows(InvalidInputException.class, () -> invokeConvert(strategy, null));
            assertTrue(ex.getMessage().toLowerCase().contains("cannot be null or empty"));
        }

        @Test
        void emptyInput_throwsInvalidInputException() {
            InvalidInputException ex = assertThrows(InvalidInputException.class, () -> invokeConvert(strategy, "   "));
            assertTrue(ex.getMessage().toLowerCase().contains("cannot be null or empty"));
        }

        @Test
        void invalidFormat_throwsWithSupportedFormatsInMessage() {
            InvalidInputException ex = assertThrows(InvalidInputException.class, () -> invokeConvert(strategy, "2/05"));
            assertTrue(ex.getMessage().startsWith("Invalid time format."));
            // ensure message lists allowed patterns
            TimeParsers.getAllowedPatterns().forEach(p -> assertTrue(ex.getMessage().contains(p)));
        }

        @Test
        void outOfRangeValues_throws() {
            InvalidInputException e1 = assertThrows(InvalidInputException.class, () -> invokeConvert(strategy, "24:00"));
            InvalidInputException e2 = assertThrows(InvalidInputException.class, () -> invokeConvert(strategy, "23:60"));
            InvalidInputException e3 = assertThrows(InvalidInputException.class, () -> invokeConvert(strategy, "-1:00"));
            e1.getMessage(); e2.getMessage(); e3.getMessage();
        }
    }

    @Nested
    @SuppressWarnings("unused")
    @DisplayName("convert: positive inputs across all allowed formats")
    class ConvertPositive {
        @Test
        void hh_colon_mm() {
            assertEquals("one o'clock", invokeConvert(strategy, "01:00"));
            assertEquals("five past two", invokeConvert(strategy, "02:05"));
        }

        @Test
        void h_colon_mm() {
            assertEquals("one o'clock", invokeConvert(strategy, "1:00"));
            assertEquals("five past two", invokeConvert(strategy, "2:05"));
        }

        @Test
        void hh_dot_mm_and_h_dot_mm() {
            assertEquals("five past two", invokeConvert(strategy, "02.05"));
            assertEquals("five past two", invokeConvert(strategy, "2.05"));
        }

        @Test
        void hhmm_compact() {
            assertEquals("five past two", invokeConvert(strategy, "0205"));
            assertEquals("noon", invokeConvert(strategy, "1200"));
        }

        @Test
        void h_dash_mm_and_hh_dash_mm() {
            assertEquals("five past two", invokeConvert(strategy, "2-05"));
            assertEquals("five past two", invokeConvert(strategy, "02-05"));
        }

        @Test
        void semanticChecks_knownPhrases() {
            assertEquals("midnight", invokeConvert(strategy, "00:00"));
            assertEquals("quarter past three", invokeConvert(strategy, "3:15"));
            assertEquals("half past ten", invokeConvert(strategy, "10:30"));
            assertEquals("quarter to six", invokeConvert(strategy, "5:45"));
            // ensure parsing and then strategy formatting are in sync around boundaries
            assertEquals("one to ten", invokeConvert(strategy, "09:59"));
        }
    }

    @Test
    @DisplayName("Sanity: strategy itself formats a parsed time as expected")
    void strategyFormattingSanity() {
        assertEquals("five past two", strategy.convertToSpokenForm(LocalTime.of(2, 5)));
    }
}


