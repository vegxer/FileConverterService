package ru.itdt.converterService.fileConverter;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class FileConverterFactory {

    public static FileConverter create(@NotNull String fileName) throws IOException {
        return switch (FilenameUtils.getExtension(fileName)) {
            case "xml" -> new XmlToJsonFileConverter(fileName);
            case "json" -> new JsonToXmlFileConverter(fileName);
            default -> throw new IllegalArgumentException("Неверное расширение файла");
        };
    }
}
