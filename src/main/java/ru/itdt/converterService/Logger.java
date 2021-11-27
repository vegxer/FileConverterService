package ru.itdt.converterService;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public final class Logger implements AutoCloseable {
    private final PrintStream logStream;

    public Logger(@NotNull File file) throws IOException {
        file.createNewFile();
        logStream = new PrintStream(file);
        System.setErr(logStream);
    }

    @Override
    public void close(){
        logStream.close();
    }
}