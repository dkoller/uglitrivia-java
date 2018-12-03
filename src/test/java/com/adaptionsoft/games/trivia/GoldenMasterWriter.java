package com.adaptionsoft.games.trivia;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.createDirectories;
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

        assertThat(actualContent, is(contentOf(goldenMasterFilePath(goldenMasterDirectory()), UTF_8)));
    }

    private static Path goldenMasterDirectory() {
        return Paths.get("src", "test", "resources");
    }

    private static String contentOf(Path goldenMasterFilePath, Charset charset) throws IOException {
        return new String(readAllBytes(goldenMasterFilePath), charset);
    }

    private static File prepareMasterFile() throws IOException {
        createDirectories(goldenMasterDirectory());
        return goldenMasterFilePath(goldenMasterDirectory()).toFile();
    }

    private static Path goldenMasterFilePath(Path goldenMasterDirectory) {
        return goldenMasterDirectory.resolve("golden-master.txt");
    }

}
