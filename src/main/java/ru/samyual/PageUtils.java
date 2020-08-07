package ru.samyual;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class PageUtils {

    // private static final String DELIMITERS = "[ ,.!?\";:\\[\\]\\()\\n\\r\\t]+";
    private static final String DELIMITERS = "[^\\p{IsAlpha}]+";

    private PageUtils() {
    }

    public static void save(final URL pageUrl, final Path pathToSave) {
        URLConnection connection;
        try {
            connection = pageUrl.openConnection();
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(pathToSave)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }

        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void parse(final Path pathToParse) throws IOException {
        final WordCounter counter = new MemoryWordCounter();
        final Document document = Jsoup.parse(pathToParse.toFile(), "UTF-8");
        Elements elements = document.body().getAllElements();
        for (Element element : elements) {
            String text = element.text();
            String[] words = text.split(DELIMITERS);
            counter.count(words);
        }
        counter.report();
        Files.delete(pathToParse);
    }
}