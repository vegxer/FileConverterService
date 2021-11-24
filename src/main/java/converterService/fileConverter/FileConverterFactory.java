package converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public class FileConverterFactory {

    public static FileConverter create(String fileName) throws IOException {
        if (FilenameUtils.getExtension(fileName).equals("xml"))
            return new XmlToJsonFileConverter(fileName);
        else if (FilenameUtils.getExtension(fileName).equals("json"))
            return new JsonToXmlFileConverter(fileName);
        else
            throw new IllegalArgumentException("Неверное расширение файла");
    }
}
