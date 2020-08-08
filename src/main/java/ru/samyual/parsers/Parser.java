package ru.samyual.parsers;

import java.io.PrintStream;
import java.nio.file.Path;

import ru.samyual.counters.WordCounter;

/**
 * Абстрактный класс Parser.
 * 
 * Предоставляет общие методы для разбора HTML страницы.
 */
public abstract class Parser {
    
    protected static final String DELIMITERS = "[^\\p{IsAlpha}]+";

    private final WordCounter counter;
    private final String delimiters;

    /**
     * Конструктор
     * 
     * @param counter {@link WordCounter} экземпляр счётчика слов
     * @param regex регулярное выражение, используемое для разбития строк на отдельные слова 
     * (определяет ограничители слов)
     */
    public Parser(WordCounter counter, String regex) {
        this.counter = counter;
        this.delimiters = null == regex ? DELIMITERS : regex;
    }

    /**
     * Конструктор
     * <p> 
     * В качестве ограничителей слов по умолчанию используются все не буквенные символы.
     * 
     * @param counter {@link WordCounter} экземпляр счётчика слов
     */
    public Parser(WordCounter counter) {
        this(counter, null);
    }

    /**
     * Метод, осуществляющий разбор файла
     * 
     * @param fileToParse путь к разбираемому файлу
     */
    public abstract void parse(Path fileToParse);

    /**
     * Разобрать строку на массив слов с использованием ограничителей
     * 
     * @param text текст, разиваемый на отдельные слова
     * @return массив слов
     */
    protected final String[] splitWithDelimiters(final String text) {
        return text.split(delimiters);
    }

    /**
     * Посчитать слово
     * 
     * @param word слово
     */
    protected final void count(final String word) {
        if (!word.isEmpty()) {
            counter.accumulate(word.toUpperCase());
        }
    }

    /**
     * Вывести отчёт. По умолчанию вывод происходит в System.out
     */
    protected final void report() {
        counter.report();
    }

    /**
     * Вывести отчёт
     * 
     * @param out поток печати
     */
    protected final void report(PrintStream out) {
        counter.report(out);
    }
}