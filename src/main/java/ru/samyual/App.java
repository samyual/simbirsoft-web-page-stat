package ru.samyual;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException
    {
        if (args.length >= 1) {
            final String url = args[0];
            Path path = Paths.get("count");
            PageUtils.save(new URL(url), path);
            PageUtils.parse(path);
        }
    }
}
