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
            Map.entry(1, "one"),
            Map.entry(2, "two"),
            Map.entry(3, "three"),
            Map.entry(4, "four"),
            Map.entry(5, "five"),
            Map.entry(6, "six"),
            Map.entry(7, "seven"),
            Map.entry(8, "eight"),
            Map.entry(9, "nine"),
            Map.entry(10, "ten"),
            Map.entry(11, "eleven")
    );

    private static final Map<Integer, String> MINUTES_LESS_THAN_20 = Map.ofEntries(
            Map.entry(1, "one"),
            Map.entry(2, "two"),
            Map.entry(3, "three"),
            Map.entry(4, "four"),
            Map.entry(5, "five"),
            Map.entry(6, "six"),
            Map.entry(7, "seven"),
            Map.entry(8, "eight"),
            Map.entry(9, "nine"),
            Map.entry(10, "ten"),
            Map.entry(11, "eleven"),
            Map.entry(12, "twelve"),
            Map.entry(13, "thirteen"),
            Map.entry(14, "fourteen"),
            Map.entry(15, "fifteen"),
            Map.entry(16, "sixteen"),
            Map.entry(17, "seventeen"),
            Map.entry(18, "eighteen"),
            Map.entry(19, "nineteen")
    );

    private static final Map<Integer, String> TENS = Map.of(
            2, "twenty",
            3, "thirty",
            4, "forty",
            5, "fifty"
    );

    public String hourKeyword(int hour){
        return HOUR_NAMES.get(hour);
    }

    public String minuteKeyword(int minute){
        if(minute<20){
            return MINUTES_LESS_THAN_20.get(minute);
        }
        return buildGreaterThanMinuteKeyword(minute);
    }

    private String buildGreaterThanMinuteKeyword(int minute) {
        int tensDigit = minute/10;
        int unitsDigit = minute%10;

        if(unitsDigit==0){
            return TENS.get(tensDigit);
        }
        return TENS.get(tensDigit) + " " + MINUTES_LESS_THAN_20.get(unitsDigit);
    }
}
