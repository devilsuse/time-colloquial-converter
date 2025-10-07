package com.andela.colloquial.converter.strategy;

import java.util.Locale;

/**
 * British English implementation of time-to-speech conversion using ResourceBundle for localization.
 *
 * This class implements the British conventions for spoken time:
 * - Special cases: midnight (00:00) and noon (12:00)
 * - Quarter past/to for 15 and 45 minutes
 * - Half past for 30 minutes
 * - Minutes 1-30: "X past [hour]"
 * - Minutes 31-34: "[hour] X"
 * - Minutes 35-59: "X to [next hour]"

 * - O'clock for exact hours
 *
 * @author Amit Kumar
 */
public class BritishTimeToSpeechStrategy extends LocalizedTimeToSpeechStrategy {

    public BritishTimeToSpeechStrategy(Locale locale) {
        super(locale);
    }
}
