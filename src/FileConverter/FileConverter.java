package FileConverter;

import FileReader.Reader;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileConverter {
    protected String fileName = null;

    public abstract void convert(String jsonFileName) throws IOException, XMLStreamException, XMLParseException, ParseException, ParserConfigurationException, TransformerException;

    public static FileConverter create(String fileName) throws IOException {
        if (Reader.getExtension(fileName).equals("xml"))
            return new XmlToJsonFileConverter(fileName);
        else if (Reader.getExtension(fileName).equals("json"))
            return new JsonToXmlFileConverter(fileName);
        else
            throw new IllegalArgumentException("Неверное расширение файла");
    }


    public String getFileName() {
        if (fileName == null)
            throw new NullPointerException("Файл не был установлен");

        return fileName;
    }

    public void setFileName(String fileName) throws FileNotFoundException {
        if (!new File(fileName).exists())
            throw new FileNotFoundException("Такого файла не существует");

        this.fileName = fileName;
    }
}
