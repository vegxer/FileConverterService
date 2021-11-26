package ru.itdt.converterService.fileConverter;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public abstract class FileConverter {
    protected File file;

    public FileConverter(@NotNull File file) {
        this.file = file;
    }

    public abstract void convert(String fileName) throws XMLStreamException, ParseException, IOException;
}
