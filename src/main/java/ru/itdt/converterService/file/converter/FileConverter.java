package ru.itdt.converterService.file.converter;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public abstract class FileConverter {
    protected final File file;

    public FileConverter(@NotNull File file) {
        this.file = file;
    }

    public abstract void convertTo(String fileName) throws XMLStreamException, ParseException, IOException, ParserConfigurationException;
}
