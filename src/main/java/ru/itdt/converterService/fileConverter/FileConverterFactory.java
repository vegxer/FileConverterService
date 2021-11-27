package ru.itdt.converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import ru.itdt.converterService.validators.FilePathValidator;
import ru.itdt.converterService.validators.Validator;

import java.io.File;
import java.io.FileNotFoundException;

public final class FileConverterFactory {
    private static final Validator<String> filePathValidator = new FilePathValidator();

    public static FileConverter create(@NotNull String fileName) throws FileNotFoundException {
        if (!filePathValidator.validate(fileName))
            throw new FileNotFoundException(String.format("Файл %s не найден", fileName));

        File file = new File(fileName);
        return switch (FilenameUtils.getExtension(fileName)) {
            case "xml" -> new XmlToJsonFileConverter(file);
            case "json" -> new JsonToXmlFileConverter(file);
            default -> throw new IllegalArgumentException("Неподдерживаемое расширение файла");
        };
    }
}
