package ru.itdt.converterService.fileReader;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Reader<T> {
    protected String fileName;


    public Reader(String fileName) throws FileNotFoundException {
        setFileName(fileName);
    }


    public abstract T readFile() throws IOException, XMLStreamException, XMLParseException, ParseException;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(@NotNull String fileName) throws FileNotFoundException {
        if (!new File(fileName).exists())
            throw new FileNotFoundException("Такого файла не существует");

        this.fileName = fileName;
    }
}
