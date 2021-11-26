package ru.itdt.converterService.Validators;

import java.io.File;

public class FilePathValidator implements Validator<String> {
    @Override
    public boolean isValid(String filePath) {
        return new File(filePath).exists();
    }
}
