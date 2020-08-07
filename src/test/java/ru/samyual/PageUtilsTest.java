package ru.samyual;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PageUtilsTest {

    private static URL pageUrl;
    private static Path pathToSave;

    @BeforeAll
    static void setUp() throws IOException {
        pageUrl = new URL("https://www.simbirsoft.com");
        pathToSave = Files.createTempFile("tmp", ".txt");
    }

    @AfterAll
    @Disabled
    static void tearDown() throws IOException {
        Files.delete(pathToSave);
    }

    @Test
    void testSave() {
        PageUtils.save(pageUrl, pathToSave);
        assertTrue(Files.exists(pathToSave));
    }
}