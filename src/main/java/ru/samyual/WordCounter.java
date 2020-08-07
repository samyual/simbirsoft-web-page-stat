package ru.samyual;

import java.io.PrintStream;

public interface WordCounter {

    void count(String[] words);

    long get(String word);

    void report();

    void report(PrintStream out);
}