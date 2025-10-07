package com.andela.colloquial.converter.strategy;

import java.time.LocalTime;

/**
 * Abstract class for time-to-speech conversion strategies
 *
 * This class implements the Template Method pattern to eliminate code duplication across different language implementations.
 *
 * The common algorithm structure is defined here, while language-specific
 * details are delegated to subclasses through abstract methods.
 *
 * Algorithm structure:
 * 1. Check for the special cases (midnight, noon)
 * 2. Check for exact hours
 * 3. Handle standard vs non-standard times
 * 4. Apply language specific formatting
 *
 *  @author Amit Kumar
 */
public abstract class AbstractTimeToSpeechStrategy implements TimeToSpeechStrategy{

    @Override
    public String convertToSpokenForm(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();

        if(isMidnight(hour, minute)){
            return getMidnightText();
        }

        if(isNoon(hour, minute)){
            return getNoonText();
        }

        if(isExactHour(minute)){
            return getExactHourText(hour);
        }

        if(isMinuteStandardFiveMultiple(minute)) {
            return standardFiveMinuteMultipleText(hour, minute);
        }

        if(formatMinutesBeforeHalfHour(minute)){
            return formatMinutesBeforeHalfHour(hour, minute);
        }

        return formatMinutesPastHalfHour(hour, minute);
    }

    protected abstract String getMidnightText();

    protected abstract String getNoonText();

    protected abstract String getExactHourText(int hour);

    protected abstract String formatMinutesBeforeHalfHour(int hour, int minute);

    protected abstract String formatMinutesPastHalfHour(int hour, int minute);

    protected abstract String standardFiveMinuteMultipleText(int hour, int minute);

    protected boolean isMidnight(int hour, int minute) {
        return hour==0 && minute==0;
    }

    protected boolean isNoon(int hour, int minute) {
        return hour == 12 && minute == 0;
    }

    protected boolean isExactHour(int minute) {
        return minute==0;
    }

    /**
     * Computes next hour.
     * To be used only when provided minute > 34
     *
     * @param hour
     * @return next hour
     */
    protected int getNextHour(int hour){
        return (hour+1)%12;
    }

    /**
     * Computes minutes remaining until the next hour.
     * To be used only when provided minute > 34
     *
     * @param minute as per current time
     * @return minutes remaining until the next hour.
     */
    protected int getMinuteToNextHour(int minute){
        return 60-minute;
    }

    private boolean formatMinutesBeforeHalfHour(int minute) {
        return minute<30;
    }

    private boolean isMinuteStandardFiveMultiple(int minute) {
        return minute==15 || minute==30 || minute==45;
    }

}
