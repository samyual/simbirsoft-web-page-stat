package ru.samyual;

import java.io.PrintStream;
import java.util.SortedMap;
import java.util.TreeMap;

public class MemoryWordCounter implements WordCounter {

    private final SortedMap<String, Long> counter = new TreeMap<>();

    @Override
    public void count(final String[] words) {
        for (final String word : words) {
            if (!word.isEmpty()) {
                counter.put(word.toUpperCase(), get(word) + 1L);
            }
        }
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
    public long get(final String word) {
        return counter.getOrDefault(word.toUpperCase(), 0L);
    }
    
}