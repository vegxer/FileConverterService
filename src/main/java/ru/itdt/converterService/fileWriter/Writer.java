package ru.itdt.converterService.fileWriter;

import org.jetbrains.annotations.NotNull;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.OutputStream;

public abstract class Writer<T> implements AutoCloseable {
    protected OutputStream outputStream;

    public Writer(@NotNull OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public abstract void write(T obj) throws IOException, ParserConfigurationException, TransformerException;

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
