package com.andela.colloquial.converter;

import java.util.Locale;

import com.andela.colloquial.converter.strategy.BritishTimeToSpeechStrategy;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

/**
 * Provides access to built-in colloquial time converters.
 * <p>
 * This class currently exposes a cached singleton for the British (en-GB) strategy.
 * Additional locale-specific converters may be added in the future as new
 * static accessors. The returned instances are thread-safe to share across
 * the application lifecycle.
 * </p>
 * 
 * @author Amit Kumar
 */
public final class TimeToColloquialConverters {
    private static final TimeToSpeechStrategy BRITISH_INSTANCE = new BritishTimeToSpeechStrategy(Locale.UK);

    private TimeToColloquialConverters() { }

    /**
     * Returns the shared British English colloquial time converter (en-GB).
     * <p>
     * The instance is a cached singleton intended for reuse and safe for
     * concurrent access.
     * </p>
     *
     * @return non-null British time-to-speech strategy
     */
    public static TimeToSpeechStrategy britishTimeToColloquialConverter(){
        return BRITISH_INSTANCE;
    }

}
