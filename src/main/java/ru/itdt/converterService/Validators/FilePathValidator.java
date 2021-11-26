package ru.itdt.converterService.Validators;

import java.io.File;

public final class FilePathValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String filePath) {
        if (filePath == null)
            return new ValidationResult(false, "Путь к файлу был null");

        if (!new File(filePath).exists())
            return new ValidationResult(false, String.format("Файла %s не существует", filePath));

        return new ValidationResult(true, "");
    }
}
