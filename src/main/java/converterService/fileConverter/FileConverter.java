package converterService.fileConverter;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileConverter {
    protected String fileName;

    public abstract void convert(String fileName)
            throws IOException, XMLStreamException, XMLParseException,
            ParseException, ParserConfigurationException, TransformerException;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(@NotNull String fileName) throws FileNotFoundException {
        if (!new File(fileName).exists())
            throw new FileNotFoundException("Такого файла не существует");

        this.fileName = fileName;
    }
}
