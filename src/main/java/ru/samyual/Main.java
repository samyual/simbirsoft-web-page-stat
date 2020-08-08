package ru.samyual;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.samyual.counters.HsqlWordCounter;
import ru.samyual.counters.WordCounter;
import ru.samyual.parsers.JsoupParser;
import ru.samyual.parsers.Parser;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        if (args.length >= 1) {

            final String url = args[0];
            try {
                parseAndReport(new URL(url));
            } catch (final MalformedURLException e) {
                log.error("Wrong URL", e);
                System.exit(1);
            }

        } else {
            System.out.println("Usage: java -jar web-page-stat <URL>");
        }
    }

    private static void parseAndReport(final URL url) {
        Path temporaryPath = null;
        try {
            temporaryPath = Files.createTempFile("wc", ".html");
        } catch (IOException e) {
            log.error("Temporary HTML file creation error", e);
            System.exit(1);
        }
        
        save(url, temporaryPath);

        WordCounter counter = new HsqlWordCounter();

        Parser parser = new JsoupParser(counter);
        parser.parse(temporaryPath);
    }

    private static void save(final URL url, Path pathToSave) {
        URLConnection connection = null;
        try {
            connection = url.openConnection();
        } catch (final IOException e) {
            log.error("Cannot open database connection", e);
            System.exit(1);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(pathToSave)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write('\n');
            }

        } catch (final IOException e) {
            log.error("Input/output error", e);
        }
    }
}
