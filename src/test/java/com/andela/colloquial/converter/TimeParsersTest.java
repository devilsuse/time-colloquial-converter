package com.andela.colloquial.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimeParsersTest {

    @Test
    @DisplayName("Allowed patterns list is immutable and ordered")
    void allowedPatternsBasics() {
        List<String> patterns = TimeParsers.getAllowedPatterns();
        assertEquals(List.of("HH:mm", "H:mm", "HH.mm", "H.mm", "HHmm", "H-mm", "HH-mm"), patterns);
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> patterns.add("X"));
        ex.getMessage();
    }

    @Test
    @DisplayName("Formatters list is immutable and aligns with patterns")
    void formattersBasics() {
        List<DateTimeFormatter> formatters = TimeParsers.getFormatters();
        assertEquals(TimeParsers.getAllowedPatterns().size(), formatters.size());
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> formatters.add(DateTimeFormatter.ISO_TIME));
        ex.getMessage();
    }

    @Nested
    @SuppressWarnings("unused")
    @DisplayName("Positive parsing cases per pattern")
    class PositiveCases {
        @Test
        void HH_colon_mm() {
            LocalTime t1 = LocalTime.parse("01:00", TimeParsers.getFormatters().get(0));
            LocalTime t2 = LocalTime.parse("23:59", TimeParsers.getFormatters().get(0));
            assertEquals(LocalTime.of(1, 0), t1);
            assertEquals(LocalTime.of(23, 59), t2);
        }

        @Test
        void H_colon_mm() {
            LocalTime t1 = LocalTime.parse("1:00", TimeParsers.getFormatters().get(1));
            LocalTime t2 = LocalTime.parse("9:05", TimeParsers.getFormatters().get(1));
            assertEquals(LocalTime.of(1, 0), t1);
            assertEquals(LocalTime.of(9, 5), t2);
        }

        @Test
        void HH_dot_mm_and_H_dot_mm() {
            LocalTime t1 = LocalTime.parse("02.05", TimeParsers.getFormatters().get(2));
            LocalTime t2 = LocalTime.parse("2.05", TimeParsers.getFormatters().get(3));
            assertEquals(LocalTime.of(2, 5), t1);
            assertEquals(LocalTime.of(2, 5), t2);
        }

        @Test
        void HHmm_compact() {
            LocalTime t1 = LocalTime.parse("0000", TimeParsers.getFormatters().get(4));
            LocalTime t2 = LocalTime.parse("2359", TimeParsers.getFormatters().get(4));
            assertEquals(LocalTime.MIDNIGHT, t1);
            assertEquals(LocalTime.of(23, 59), t2);
        }

        @Test
        void H_dash_mm_and_HH_dash_mm() {
            LocalTime t1 = LocalTime.parse("2-05", TimeParsers.getFormatters().get(5));
            LocalTime t2 = LocalTime.parse("02-05", TimeParsers.getFormatters().get(6));
            assertEquals(LocalTime.of(2, 5), t1);
            assertEquals(LocalTime.of(2, 5), t2);
        }
    }

    @Nested
    @SuppressWarnings("unused")
    @DisplayName("Negative parsing cases per pattern")
    class NegativeCases {
        @Test
        void HH_colon_mm_out_of_range_or_malformed() {
            DateTimeParseException e1 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("24:00", TimeParsers.getFormatters().get(0)));
            DateTimeParseException e2 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("23:60", TimeParsers.getFormatters().get(0)));
            DateTimeParseException e3 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("-1:00", TimeParsers.getFormatters().get(0)));
            DateTimeParseException e4 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("1:00", TimeParsers.getFormatters().get(0))); // needs HH
            // touch messages to avoid unused warnings
            e1.getMessage(); e2.getMessage(); e3.getMessage(); e4.getMessage();
        }

        @Test
        void H_colon_mm_malformed() {
            DateTimeParseException e1 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("01:0", TimeParsers.getFormatters().get(1)));
            e1.getMessage();
        }

        @Test
        void HH_dot_mm_and_H_dot_mm_malformed() {
            DateTimeParseException e1 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("2.5", TimeParsers.getFormatters().get(2)));
            DateTimeParseException e2 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("2.5", TimeParsers.getFormatters().get(3)));
            DateTimeParseException e3 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("02:05", TimeParsers.getFormatters().get(2))); // wrong separator
            e1.getMessage(); e2.getMessage(); e3.getMessage();
        }

        @Test
        void HHmm_compact_wrong_length_or_range() {
            DateTimeParseException e1 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("205", TimeParsers.getFormatters().get(4)));
            DateTimeParseException e2 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("2460", TimeParsers.getFormatters().get(4)));
            DateTimeParseException e3 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("-205", TimeParsers.getFormatters().get(4)));
            e1.getMessage(); e2.getMessage(); e3.getMessage();
        }

        @Test
        void H_dash_mm_and_HH_dash_mm_mismatch() {
            // wrong separator
            DateTimeParseException e1 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("2:05", TimeParsers.getFormatters().get(5)));
            // requires two-digit hour for index 6
            DateTimeParseException e2 = assertThrows(DateTimeParseException.class, () -> LocalTime.parse("2-05", TimeParsers.getFormatters().get(6)));
            e1.getMessage(); e2.getMessage();
        }
    }
}


