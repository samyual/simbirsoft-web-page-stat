package ru.samyual.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.samyual.counters.WordCounter;

/**
 * Класс JsopParser.
 * <p>
 * Выполняет разбор HTML файла с использованием библиотеки Jsoup
 */
public final class JsoupParser extends Parser {

    private static final Logger log = LoggerFactory.getLogger(JsoupParser.class);

    public JsoupParser(WordCounter counter) {
        super(counter);
    }

    public JsoupParser(WordCounter counter, String regex) {
        super(counter, regex);
    }

    @Override
    public void parse(Path fileToParse) {
        Document document = null;
        try {
            document = Jsoup.parse(fileToParse.toFile(), "UTF-8");
        } catch (IOException e) {
            log.error("Error open file to parse", e);
            System.exit(1);
        }

        final Elements elements = document.body().getAllElements();
        elements.forEach(element -> {
            final String text = element.text();
            final String[] words = splitWithDelimiters(text);
            for (final String word : words) {
                count(word);
            }
        });

        report();

        try {
            Files.deleteIfExists(fileToParse);
		} catch (IOException e) {
            log.error("Error deleting temporary file", e);
            System.exit(1);
		};
    }

}