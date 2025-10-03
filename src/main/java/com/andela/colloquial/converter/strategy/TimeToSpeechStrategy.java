package com.andela.colloquial.converter.strategy;


import java.time.LocalTime;

/**
 * Strategy interface for converting time to colloquial form.
 *
 * This interface follows the Strategy pattern, allowing different
 * implementations for various colloquial time formats (British, American, etc.).
 *
 * @author Amit Kumar
 * @version 1.0
 */
public interface TimeToSpeechStrategy {

    /**
     * Converts a LocalTime object to its spoken form.
     *
     * @param time the time to convert
     * @return the colloquial form of the time
     */
    String convertToSpokenForm(LocalTime time);
}
