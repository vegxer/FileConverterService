package fileReader;

import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Reader<T> {
    protected String fileName = null;

    public abstract T readFile() throws IOException, XMLStreamException, XMLParseException, ParseException;


    public String getFileName() {
        if (fileName == null)
            throw new NullPointerException("Имя файла не было установлено");

        return fileName;
    }

    public void setFileName(String fileName) throws FileNotFoundException {
        if (!new File(fileName).exists())
            throw new FileNotFoundException("Такого файла не существует");

        this.fileName = fileName;
    }
}
