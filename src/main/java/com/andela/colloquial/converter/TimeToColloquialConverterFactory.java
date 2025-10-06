package com.andela.colloquial.converter;

import java.util.Locale;

import com.andela.colloquial.converter.strategy.BritishTimeToSpeechStrategy;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

public final class TimeToColloquialConverterFactory {
    private static final TimeToSpeechStrategy BRITISH_INSTANCE = new BritishTimeToSpeechStrategy(Locale.UK);

    private TimeToColloquialConverterFactory() { }

    public static TimeToSpeechStrategy britishTimeToColloquialConverter(){
        return BRITISH_INSTANCE;
    }

}
