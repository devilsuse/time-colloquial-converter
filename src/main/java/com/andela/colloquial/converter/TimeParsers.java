package com.andela.colloquial.converter;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Centralizes whitelisted time parsing patterns and their corresponding
 * {@link DateTimeFormatter} instances.
 * <p>
 * The exposed formatters and pattern lists are immutable and safe to share
 * across threads. This utility limits parsing to approved patterns
 * and avoids accepting arbitrary user-defined formatter strings.
 * </p>
 */
public final class TimeParsers {

    private TimeParsers(){}

    /**
     * Whitelisted time patterns under system control. Ordering determines
     * preference when attempting multiple parses.
     */
    private static final List<String> TIME_PATTERNS = List.of(
        "HH:mm", "H:mm", 
        "HH.mm", "H.mm", "HHmm",
        "H-mm", "HH-mm"
    );

    /**
     * Immutable list of formatters derived from {@link #TIME_PATTERNS}.
     */
    private static final List<DateTimeFormatter> FORMATTERS;

    static {
        List<DateTimeFormatter> list = new ArrayList<>();
        for (String p : TIME_PATTERNS) {
            list.add(new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(p)
                .toFormatter());
        }
        FORMATTERS = Collections.unmodifiableList(list);
    }

    /**
     * Returns the immutable list of approved {@link DateTimeFormatter}
     * instances for time parsing.
     *
     * @return unmodifiable list of formatters, never null
     */
    public static List<DateTimeFormatter> getFormatters() {
        return FORMATTERS;
    }

    /**
     * Returns the immutable list of approved pattern strings used to build the
     * formatters in {@link #getFormatters()}.
     *
     * @return unmodifiable list of pattern strings, never null
     */
    public static List<String> getAllowedPatterns() {
        return TIME_PATTERNS;
    }
    
}
