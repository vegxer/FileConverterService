package ru.itdt.converterService;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public final class Logger implements AutoCloseable {
    private final PrintStream logStream;
    private static final String PATH = "out/logs";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Logger(@NotNull String fileName) throws IOException {
        new File(PATH).mkdirs();
        new File(PATH + "/" + fileName).createNewFile();

        logStream = new PrintStream(PATH + "/" + fileName);
        System.setErr(logStream);
    }

    @Override
    public void close(){
        logStream.close();
    }
}
