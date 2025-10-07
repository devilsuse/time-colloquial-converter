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

class TimeToColloquialConvertersTest {

    @Test
    @DisplayName("britishTimeToColloquialConverter returns a cached singleton instance (provider)")
    void returnsSingleton() {
        TimeToSpeechStrategy instance1 = TimeToColloquialConverters.britishTimeToColloquialConverter();
        TimeToSpeechStrategy instance2 = TimeToColloquialConverters.britishTimeToColloquialConverter();

        assertNotNull(instance1);
        assertSame(instance1, instance2, "Factory should return the same cached instance");
        assertInstanceOf(BritishTimeToSpeechStrategy.class, instance1);
    }

    @Test
    @DisplayName("britishTimeToColloquialConverter returns same instance across repeated calls")
    void repeatedCallsReturnSameInstance() {
        TimeToSpeechStrategy first = TimeToColloquialConverters.britishTimeToColloquialConverter();
        boolean allSame = IntStream.range(0, 10)
                .mapToObj(i -> TimeToColloquialConverters.britishTimeToColloquialConverter())
                .allMatch(inst -> inst == first);
        assertTrue(allSame, "All calls should return the same singleton instance");
    }
}




