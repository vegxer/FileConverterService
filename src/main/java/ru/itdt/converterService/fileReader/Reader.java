package ru.itdt.converterService.fileReader;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class Reader<T> implements AutoCloseable {
    protected InputStream inputStream;

    public Reader(@NotNull InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public abstract T readFile() throws IOException, XMLStreamException, XMLParseException, ParseException;

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
