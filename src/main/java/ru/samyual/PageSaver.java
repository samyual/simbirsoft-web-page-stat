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

public final class PageSaver {

    private final URL pageUrl;
    private final URLConnection connection;

    public PageSaver(final URL pageUrl) {
        this.pageUrl = pageUrl;
        try {
            connection = pageUrl.openConnection();
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void save(final Path pathToSave) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(pathToSave)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
        }
    }
}