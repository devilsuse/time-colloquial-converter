package com.andela.colloquial.converter.strategy;

import java.time.LocalTime;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * An abstract base class for time formatters that use a ResourceBundle
 * to localize time-related strings (e.g., "one", "past", "quarter").
 *
 * @author Amit Kumar
 * @version 1.0
 */

public abstract class LocalizedTimeToSpeechStrategy extends AbstractTimeToSpeechStrategy{
    private static final String HOUR_KEY_PREFIX = "hour.";
    private static final String MINUTE_KEY_PREFIX = "minute.";
    private static final String TENS_KEY_PREFIX = "tens.";

    private static final String BUNDLE_BASE_NAME = "messages";
    protected final ResourceBundle bundle;

    protected LocalizedTimeToSpeechStrategy(Locale locale){
        this.bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }

    /**
     * Retrieves the localized string associated with the given key from the resource bundle.
     * This method serves as a convenient wrapper around ResourceBundle.getString().
     * * @param key The key of the localized string to retrieve.
     * @return The localized string value.
     * //@throws MissingResourceException if no object for the given key can be found.
     */
    protected String getMessage(String key){
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }

    @Override
    public String convertToSpokenForm(LocalTime time) {
        return super.convertToSpokenForm(time);
    }

    @Override
    protected String getMidnightText(){
        return getMessage("midnight");
    }

    @Override
    protected String getNoonText(){
        return getMessage("noon");
    }

    @Override
    protected String getExactHourText(int hour){
        return join(getMessage(HOUR_KEY_PREFIX + hour%12),
                getMessage("oclock"));
    }

    //@Override
    private String getHourText(int hour){
        return getMessage(HOUR_KEY_PREFIX + hour%12);
    }

    @Override
    protected String formatMinutesBeforeHalfHour(int hour, int minute) {
        return join(resolveMinuteLessThan30(minute),
                getMessage("past"),
                getHourText(hour));
    }

    /**
     *
     * @param minute in the expected range
     * @return
     */
    private String getMinuteText(int minute){
        return getMessage(MINUTE_KEY_PREFIX + minute);
    }

    /**
     * Resolve the colloquial phrase for a minute less than 30.
     */
    private String resolveMinuteLessThan30(int minute) {
        if(minute<20){
            return getMinuteText(minute);
        }

        //As this method handles minute<30 so tens digit has to be max 2
        int unitsDigit = minute%10;
        if(unitsDigit==0){
            return getMessage(TENS_KEY_PREFIX + "2");
        }
        return join(getMessage(TENS_KEY_PREFIX + "2"), getMinuteText(unitsDigit));
    }

    @Override
    protected String formatMinutesPastHalfHour(int hour, int minute) {
        if (minute > 30 && minute <= 34) {
            return formatEarlyThirties(hour, minute);
        }
        return formatMinutesToNextHour(hour, minute);
    }

    private String formatEarlyThirties(int hour, int minute) {
        String hourText = getHourText(hour);
        String minuteText = join(getMessage(TENS_KEY_PREFIX + "3"), getMinuteText(minute - 30));
        return join(hourText, minuteText);
    }

    private String formatMinutesToNextHour(int hour, int minute) {
        int nextHour = getNextHour(hour);
        String minutesToNextHourText = resolveMinuteLessThan30(60 - minute);
        return join(minutesToNextHourText, getMessage("to"), getHourText(nextHour));
    }

    @Override
    protected String standardFiveMinuteMultipleText(int hour, int minute) {
        String hourText = getHourText(hour);
        if (minute == 15){
            return join(getMessage("quarter_past"), hourText);
        }

        if(minute < 30){
            return join(resolveMinuteLessThan30(minute), getMessage("past"), hourText);
        }

        if(minute==30){
            return join(getMessage("half_past"), hourText);
        }

        int nextHour = getNextHour(hour);
        if(minute == 45){
            return join(getMessage("quarter_to"), getHourText(nextHour));
        }
        return join(resolveMinuteLessThan30(60 - minute), getMessage("to"), getHourText(nextHour));
    }

    private String join(String... parts) {
        return String.join(" ", parts);
    }
}
