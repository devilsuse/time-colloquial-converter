package com.andela.colloquial.converter;

import com.andela.colloquial.converter.strategy.BritishTimeToSpeechStrategy;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

import java.util.Locale;

public class TimeToColloquialConverterFactory {
    public static TimeToSpeechStrategy britishTimeToColloquialConverter(){
        return new BritishTimeToSpeechStrategy(Locale.UK);
    }

}
