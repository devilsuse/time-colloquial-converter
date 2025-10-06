package com.andela.colloquial.converter;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.andela.colloquial.converter.strategy.BritishTimeToSpeechStrategy;
import com.andela.colloquial.converter.strategy.TimeToSpeechStrategy;

class TimeToColloquialConverterFactoryTest {

    @Test
    @DisplayName("britishTimeToColloquialConverter returns a cached singleton instance")
    void returnsSingleton() {
        TimeToSpeechStrategy instance1 = TimeToColloquialConverterFactory.britishTimeToColloquialConverter();
        TimeToSpeechStrategy instance2 = TimeToColloquialConverterFactory.britishTimeToColloquialConverter();

        assertNotNull(instance1);
        assertSame(instance1, instance2, "Factory should return the same cached instance");
        assertInstanceOf(BritishTimeToSpeechStrategy.class, instance1);
    }

    @Test
    @DisplayName("britishTimeToColloquialConverter returns same instance across repeated calls")
    void repeatedCallsReturnSameInstance() {
        TimeToSpeechStrategy first = TimeToColloquialConverterFactory.britishTimeToColloquialConverter();
        boolean allSame = IntStream.range(0, 10)
                .mapToObj(i -> TimeToColloquialConverterFactory.britishTimeToColloquialConverter())
                .allMatch(inst -> inst == first);
        assertTrue(allSame, "All calls should return the same singleton instance");
    }
}


