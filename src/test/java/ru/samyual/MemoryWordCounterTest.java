package ru.samyual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.samyual.counters.MemoryWordCounter;
import ru.samyual.counters.WordCounter;

public class MemoryWordCounterTest {

    @Test
    void testAccumulateAndCounter() {
        WordCounter counter = new MemoryWordCounter();
        assertEquals(counter.getCounter("WORD"), 0);
        counter.accumulate("WORD");
        assertEquals(counter.getCounter("WORD"), 1);
    }
}