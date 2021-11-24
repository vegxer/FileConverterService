package converterService.fileWriter;

import org.jetbrains.annotations.NotNull;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public abstract class Writer<T> {
    protected String fileName;


    public Writer(String fileName) {
        setFileName(fileName);
    }


    public abstract void write(T obj) throws IOException, ParserConfigurationException, TransformerException;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(@NotNull String fileName) {
        this.fileName = fileName;
    }
}
