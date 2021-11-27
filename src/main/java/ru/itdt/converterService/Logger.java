package ru.itdt.converterService;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystemException;

public final class Logger implements AutoCloseable {
    private final PrintStream logStream;
    private static final String PATH = "out/logs";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Logger(@NotNull String fileName) throws FileSystemException {
        String creationPath = PATH + "/" + fileName;

        try {
            new File(PATH).mkdirs();
            new File(creationPath).createNewFile();
            logStream = new PrintStream(creationPath);
            System.setErr(logStream);
        } catch (IOException fileCreationExc) {
            throw new FileSystemException(String.format("Не удалось создать файл по пути %s", creationPath));
        }
    }

    @Override
    public void close(){
        logStream.close();
    }
}
