package com.adaptionsoft.games.trivia;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GoldenMasterWriter {

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(new FileOutputStream(prepareMasterFile()), true));
        GoldenMasterRunner.main(new String[0]);
    }

    @Test
    public void shouldProduceExpectedGoldenMasterOutput() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1);
        System.setOut(new PrintStream(out, true));
        GoldenMasterRunner.main(new String[0]);
        String actualContent = out.toString(UTF_8.name());

        Path goldenMasterDirectory = Paths.get("src", "test", "resources");
        Path goldenMasterFilePath = goldenMasterDirectory.resolve("golden-master.txt");
        assertThat(actualContent, is(contentOf(goldenMasterFilePath)));
    }

    private String contentOf(Path goldenMasterFilePath) throws IOException {
        return new String(readAllBytes(goldenMasterFilePath), UTF_8);
    }

    private static File prepareMasterFile() throws IOException {
        Path goldenMasterDirectory = Paths.get("src", "test", "resources");
        Files.createDirectories(goldenMasterDirectory);
        Path goldenMasterFilePath = goldenMasterDirectory.resolve("golden-master.txt");
        return goldenMasterFilePath.toFile();
    }

}
