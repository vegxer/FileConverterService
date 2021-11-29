package ru.itdt.converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;

public final class FileConverterFactory {

    public static FileConverter create(@NotNull String fileName) throws FileNotFoundException {
        if (validateFilePath(fileName))
            throw new FileNotFoundException(String.format("Файл %s не найден", fileName));

        File file = new File(fileName);
        return switch (FilenameUtils.getExtension(fileName)) {
            case "xml" -> new XmlToJsonFileConverter(file);
            case "json" -> new JsonToXmlFileConverter(file);
            default -> throw new IllegalArgumentException("Неподдерживаемое расширение файла");
        };
    }

    private static boolean validateFilePath(String filePath) {
        return new File(filePath).exists();
    }
}
