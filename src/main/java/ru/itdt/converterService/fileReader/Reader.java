package ru.itdt.converterService.fileReader;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Reader<T> implements AutoCloseable {
    protected FileInputStream inputStream;

    public Reader(@NotNull FileInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public abstract T readFile() throws IOException, XMLStreamException, ParseException;

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
