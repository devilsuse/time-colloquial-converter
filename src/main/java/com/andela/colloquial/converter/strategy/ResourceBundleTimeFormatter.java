package com.andela.colloquial.converter.strategy;

import java.util.ResourceBundle;

/**
 * An abstract base class for time formatters that use a ResourceBundle
 * to localize time-related strings (e.g., "one", "past", "quarter").
 *
 * @author Amit Kumar
 * @version 1.0
 */

public abstract class ResourceBundleTimeFormatter implements TimeToSpeechStrategy{
    protected final ResourceBundle bundle;

    protected ResourceBundleTimeFormatter(ResourceBundle bundle){
        this.bundle = bundle;
    }

    /**
     * Retrieves the localized string associated with the given key from the resource bundle.
     * This method serves as a convenient wrapper around ResourceBundle.getString().
     * * @param key The key of the localized string to retrieve.
     * @return The localized string value.
     * @throws MissingResourceException if no object for the given key can be found.
     */
    protected String get(String key){
        return bundle.getString(key);
    }

    /**
     * Helper method to get the spoken name of an hour (1-12).
     * @param hour The hour in 24-hour format (0-23).
     * @return The 12-hour spoken name (e.g., "one", "twelve").
     */
    protected String getHourName(int hour) {
        // Convert 24-hour to 12-hour clock (1-12)
        switch (hour % 12) {
            case 0: return get("twelve");
            case 1: return get("one");
            case 2: return get("two");
            case 3: return get("three");
            case 4: return get("four");
            case 5: return get("five");
            case 6: return get("six");
            case 7: return get("seven");
            case 8: return get("eight");
            case 9: return get("nine");
            case 10: return get("ten");
            case 11: return get("eleven");
            default: throw new IllegalArgumentException("Invalid hour: " + hour);
        }
    }
}
