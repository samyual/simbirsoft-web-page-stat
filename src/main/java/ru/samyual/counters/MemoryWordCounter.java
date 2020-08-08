package ru.samyual.counters;

import java.io.PrintStream;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Класс MemoryWordCounter
 * <p>
 * Реализует счётчик слов с хранением статистики в оперативной памяти.
 */
public class MemoryWordCounter implements WordCounter {

    private final SortedMap<String, Long> counter = new TreeMap<>();

    @Override
    public void accumulate(final String word) {
        counter.put(word, getCounter(word) + 1);
    }

    @Override
    public void report() {
        report(System.out);
    }

    @Override
    public void report(final PrintStream out) {
        counter.forEach((word, count) -> out.println(word + " - " + count));
    }

    @Override
    public long getCounter(final String word) {
        return counter.getOrDefault(word.toUpperCase(), 0L);
    }
}