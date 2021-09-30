package FileWriter;

import FileReader.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public abstract class Writer {
    protected String fileName = null;

    public abstract void write(Object obj) throws IOException, ParserConfigurationException, TransformerException;

    public static Writer create(String fileName) throws IOException {
        if (Reader.getExtension(fileName).equals("json"))
            return new MusicGenresWriter(fileName);
        else if (Reader.getExtension(fileName).equals("xml"))
            return new MusicBandsWriter(fileName);
        else
            throw new IllegalArgumentException("Неверное расширение файла");
    }


    public String getFileName() {
        if (fileName == null)
            throw new NullPointerException("Имя файла не было установлено");

        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
