package com.adaptionsoft.games.trivia;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static org.junit.Assert.assertEquals;

public class CheckGoldenMaster {

    @Test
    public void shouldProduceExpectedGoldenMasterOutput() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1);
        System.setOut(new PrintStream(out, true));
        GoldenMasterRunner.sampleGames(10000);
        String actualContent = out.toString(UTF_8.name());

        assertEquals(contentOf(goldenMasterFilePath(goldenMasterDirectory()), UTF_8), actualContent);
    }

    static Path goldenMasterDirectory() {
        return Paths.get("src", "test", "resources");
    }

    private static String contentOf(Path goldenMasterFilePath, Charset charset) throws IOException {
        return new String(readAllBytes(goldenMasterFilePath), charset);
    }

    static Path goldenMasterFilePath(Path goldenMasterDirectory) {
        return goldenMasterDirectory.resolve("golden-master.txt");
    }

}
