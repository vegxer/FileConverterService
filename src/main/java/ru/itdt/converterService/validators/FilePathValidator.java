package ru.itdt.converterService.validators;

import java.io.File;

public final class FilePathValidator implements Validator<String> {
    @Override
    public boolean validate(String filePath) {
        if (filePath == null)
            return false;

        return new File(filePath).exists();
    }
}
