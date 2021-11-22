package fileWriter;

import fileExtension.FileExtension;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public abstract class Writer<T> {
    protected String fileName = null;

    public abstract void write(T obj) throws IOException, ParserConfigurationException, TransformerException;


    public String getFileName() {
        if (fileName == null)
            throw new NullPointerException("Имя файла не было установлено");

        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
