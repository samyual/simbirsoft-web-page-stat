package ru.samyual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.samyual.counters.HsqlWordCounter;
import ru.samyual.counters.WordCounter;

public class HsqlWordCounterTest {

    @Test
    void testAccumulateAndCounter() {
        WordCounter counter = new HsqlWordCounter();
        assertEquals(counter.getCounter("WORD"), 0);
        counter.accumulate("WORD");
        assertEquals(counter.getCounter("WORD"), 1);
    }
}