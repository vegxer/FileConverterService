package converterService.fileConverter;

import converterService.FileExtension;

import java.io.IOException;

public class FileConverterFactory {

    public static FileConverter create(String fileName) throws IOException {
        if (FileExtension.getExtension(fileName).equals("xml"))
            return new XmlToJsonFileConverter(fileName);
        else if (FileExtension.getExtension(fileName).equals("json"))
            return new JsonToXmlFileConverter(fileName);
        else
            throw new IllegalArgumentException("Неверное расширение файла");
    }
}
