package FileReader;

import org.json.simple.parser.ParseException;

import javax.management.modelmbean.XMLParseException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Reader {
    protected String fileName = null;

    public abstract Object readFile() throws IOException, XMLStreamException, XMLParseException, ParseException;

    public static Reader create(String fileName) throws IOException {
        if (getExtension(fileName).equals("xml"))
            return new MusicBandsReader(fileName);
        else if (getExtension(fileName).equals("json"))
            return new MusicGenresReader(fileName);
        else
            throw new IllegalArgumentException("Неверное расширение файла");
    }

    public static String getExtension(String file) throws IOException
    {
        if (file.indexOf('.') == -1 || file.indexOf('.') == file.length() - 1)
            throw new IOException("Incorrect filePath input");

        return file.substring(file.lastIndexOf('.') + 1);
    }


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
