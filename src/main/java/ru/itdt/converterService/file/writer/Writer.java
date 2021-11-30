package ru.itdt.converterService.file.writer;

import org.jetbrains.annotations.NotNull;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class Writer<T> implements AutoCloseable {
    protected final FileOutputStream outputStream;

    public Writer(@NotNull FileOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public abstract void write(T obj) throws ParserConfigurationException, IOException;

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
