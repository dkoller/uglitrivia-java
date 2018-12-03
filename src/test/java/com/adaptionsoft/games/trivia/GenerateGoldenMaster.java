package com.adaptionsoft.games.trivia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static java.nio.file.Files.createDirectories;

public class GenerateGoldenMaster {

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(new FileOutputStream(prepareMasterFile()), true));
        GoldenMasterRunner.sampleGames(10000);
    }

    static File prepareMasterFile() throws IOException {
        createDirectories(CheckGoldenMaster.goldenMasterDirectory());
        return CheckGoldenMaster.goldenMasterFilePath(CheckGoldenMaster.goldenMasterDirectory()).toFile();
    }
}
