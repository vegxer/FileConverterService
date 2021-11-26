package ru.itdt.converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;
import ru.itdt.converterService.Validators.FilePathValidator;
import ru.itdt.converterService.Validators.ValidationResult;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class FileConverterFactory {

    public static FileConverter create(String fileName) throws IOException {
        ValidationResult result = new FilePathValidator().validate(fileName);
        if (!result.isValid())
            throw new FileNotFoundException(result.exceptionMessage());

        return switch (FilenameUtils.getExtension(fileName)) {
            case "xml" -> new XmlToJsonFileConverter(fileName);
            case "json" -> new JsonToXmlFileConverter(fileName);
            default -> throw new IllegalArgumentException("Неподдерживаемое расширение файла");
        };
    }
}
