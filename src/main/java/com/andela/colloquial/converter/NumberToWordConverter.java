package com.andela.colloquial.converter;

import java.util.Map;

/**
 * Converts numbers to the colloquial English equivalents
 *
 * This class encapsulated all the number-to-word conversion logic,
 * following the Single Responsibility Principle. It provides a
 * clear, type-safe interface for converting hours and minutes to the colloquial forms.
 *
 *  * @author Amit Kumar
 *  * @version 1.0
 */
public class NumberToWordConverter {

    private static final Map<Integer, String> HOUR_NAMES = Map.ofEntries(
            Map.entry(0, "twelve"),
            Map.entry(1, "one")

    );
}
