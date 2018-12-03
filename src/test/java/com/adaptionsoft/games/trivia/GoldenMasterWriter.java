package com.adaptionsoft.games.trivia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GoldenMasterWriter {

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(new FileOutputStream(prepareMasterFile())));
        GoldenMasterRunner.main(new String[0]);
    }

    private static File prepareMasterFile() throws IOException {
        Path goldenMasterDirectory = Paths.get("src", "test", "resources");
        Files.createDirectories(goldenMasterDirectory);
        return goldenMasterDirectory.resolve("golden-master.txt").toFile();
    }
}
