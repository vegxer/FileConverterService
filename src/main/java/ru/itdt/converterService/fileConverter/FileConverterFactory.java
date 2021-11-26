package ru.itdt.converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;
import ru.itdt.converterService.Validators.FilePathValidator;
import ru.itdt.converterService.Validators.ValidationResult;
import ru.itdt.converterService.Validators.Validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class FileConverterFactory {
    private static final Validator<String> filePathValidator = new FilePathValidator();

    public static FileConverter create(String fileName) throws IOException {
        ValidationResult result = filePathValidator.validate(fileName);
        if (!result.isValid())
            throw new FileNotFoundException(result.exceptionMessage());

        File file = new File(fileName);
        return switch (FilenameUtils.getExtension(fileName)) {
            case "xml" -> new XmlToJsonFileConverter(file);
            case "json" -> new JsonToXmlFileConverter(file);
            default -> throw new IllegalArgumentException("Неподдерживаемое расширение файла");
        };
    }
}
