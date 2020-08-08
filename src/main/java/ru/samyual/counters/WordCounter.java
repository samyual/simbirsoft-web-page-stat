package ru.samyual.counters;

import java.io.PrintStream;

/**
 * Интерфейс WordCounter (Счётчик слов)
 * <p>
 * Определяет методы, используемые для подсчёта слов.
 */
public interface WordCounter {

    /**
     * Посчитать слово.
     * 
     * @param word слово
     */
    void accumulate(String word);

    /**
     * Получить количество использований слова.
     * 
     * @param word
     * @return количество использований слова.
     */
    long getCounter(String word);

    /**
     * Вывести статистику использования слов.
     */
    void report();

    /**
     * Вывести статистику использования слов.
     * 
     * @param out поток печати
     */
    void report(PrintStream out);
}